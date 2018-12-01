package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.service.impl.util.ExchangeRateService;
import com.gordeev.movieland.entity.Currency;
import com.gordeev.movieland.entity.RequestParameter;
import com.gordeev.movieland.service.impl.util.ParallelMovieEnrichmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private ParallelMovieEnrichmentService parallelMovieEnrichmentService;
    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ExchangeRateService exchangeRateService;

    @Override
    public List<Movie> getAll(RequestParameter requestParameter) {
        List<Movie> movies = movieDao.getAll(requestParameter);

        parallelMovieEnrichmentService.enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        List<Movie> movies = movieDao.getThreeRandomMovie();

        parallelMovieEnrichmentService.enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        List<Integer> moviesIdsByGenre = genreService.getMoviesIdsByGenreId(genreId);
        List<Movie> movies = movieDao.getMoviesByIds(moviesIdsByGenre);

        parallelMovieEnrichmentService.enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = movieDao.getAll();

        parallelMovieEnrichmentService.enrich(movies);

        return movies;
    }

    @Override
    public Movie getMovieById(int movieId, Currency currency) {
        List<Movie> movies = movieDao.getMoviesByIds(Collections.singletonList(movieId));

        parallelMovieEnrichmentService.enrich(movies);

        Movie movie = movies.get(0);
        if (currency != Currency.UAH) {
            Map<Currency, Double> exchangeRatesMap = exchangeRateService.getExchangeRatesMap();
            double rate = exchangeRatesMap.get(currency);
            movie.setPrice(movie.getPrice() / rate);
        }

        return movie;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, isolation = Isolation.READ_COMMITTED)
    public void add(Movie movie) {
        movieDao.add(movie);
        countryService.addToMovie(movie);
        genreService.addToMovie(movie);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(Movie movie) {
        movieDao.update(movie);

        countryService.removeFromMovie(movie);
        countryService.addToMovie(movie);

        genreService.removeFromMovie(movie);
        genreService.addToMovie(movie);
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Autowired
    public void setParallelMovieEnrichmentService(ParallelMovieEnrichmentService parallelMovieEnrichmentService) {
        this.parallelMovieEnrichmentService = parallelMovieEnrichmentService;
    }
}