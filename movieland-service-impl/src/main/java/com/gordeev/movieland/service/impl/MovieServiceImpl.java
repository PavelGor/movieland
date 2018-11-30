package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.Review;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.service.ReviewService;
import com.gordeev.movieland.service.impl.util.ExchangeRateService;
import com.gordeev.movieland.entity.Currency;
import com.gordeev.movieland.entity.RequestParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private ExchangeRateService exchangeRateService;

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

        enrich(movies);

        Movie movie = movies.get(0);

        List<Review> reviews = reviewService.getByMovieId(movie.getId());
        movie.setReviews(reviews);

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

    private void enrich(List<Movie> movies) {
        genreService.enrich(movies);
        countryService.enrich(movies);
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
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setExchangeRateService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
}