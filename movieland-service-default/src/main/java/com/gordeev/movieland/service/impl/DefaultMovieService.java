package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.Review;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.service.ReviewService;
import com.gordeev.movieland.service.impl.util.ExchangeRateService;
import com.gordeev.movieland.vo.Currency;
import com.gordeev.movieland.vo.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private ExchangeRateService exchangeRateService;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, GenreService genreService, CountryService countryService, ReviewService reviewService, ExchangeRateService exchangeRateService) {
        this.countryService = countryService;
        this.genreService = genreService;
        this.movieDao = movieDao;
        this.reviewService = reviewService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public List<Movie> getAll(RequestParameter requestParameter) {
        List<Movie> movies = movieDao.getAll(requestParameter);

        enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        List<Movie> movies = movieDao.getThreeRandomMovie();

        enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        List<Integer> moviesIdsByGenre = genreService.getMoviesIdsByGenreId(genreId);
        List<Movie> movies = movieDao.getMoviesByIds(moviesIdsByGenre);

        enrich(movies);

        return movies;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = movieDao.getAll();

        enrich(movies);

        return movies;
    }

    @Override
    public Movie getMovieById(int movieId, Currency currency) {
        List<Movie> movies = movieDao.getMoviesByIds(Collections.singletonList(movieId));

        //enrich with genres and countries
        enrich(movies);

        Movie movie = movies.get(0);

        //enrich with reviews
        List<Review> reviews = reviewService.getByMovieId(movie.getId());
        movie.setReviews(reviews);

        //set currency
        Map<Currency, Double> exchangeRatesMap = exchangeRateService.getExchangeRatesMap();
        double rate = exchangeRatesMap.get(currency);
        if (currency != Currency.UAH) { movie.setPrice(movie.getPrice()/rate);}

        return movie;
    }

    private void enrich(List<Movie> movies) {
        genreService.enrich(movies);
        countryService.enrich(movies);
    }
}