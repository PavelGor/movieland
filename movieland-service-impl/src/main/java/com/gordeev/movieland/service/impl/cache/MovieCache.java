package com.gordeev.movieland.service.impl.cache;

import com.gordeev.movieland.entity.Currency;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.RequestParameter;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.service.impl.util.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Service
public class MovieCache implements MovieService{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;
    private ExchangeRateService exchangeRateService;

    private final Map<Integer, SoftReference<Movie>> movieCache = new ConcurrentHashMap<>();

    @Autowired
    public MovieCache(MovieService movieService, ExchangeRateService exchangeRateService) {
        this.movieService = movieService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public List<Movie> getAll(RequestParameter requestParameter) {
        return movieService.getAll(requestParameter);
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        return movieService.getThreeRandomMovie();
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        return movieService.getMoviesByGenreId(genreId);
    }

    @Override
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @Override
    public Movie getMovieById(int movieId, Currency currency) {
        logger.info("Start getting movie from cache by movieId: {}", movieId);
        long startTime = System.currentTimeMillis();

        Movie movie;
        SoftReference<Movie> movieSoftReference =  movieCache.get(movieId);
        if (movieSoftReference == null){
            movie = movieService.getMovieById(movieId, currency);
            movieCache.put(movieId, new SoftReference<>(movie));
        } else {
            movie = movieSoftReference.get();
        }

        if (currency != Currency.UAH) {
            Map<Currency, Double> exchangeRatesMap = exchangeRateService.getExchangeRatesMap();
            double rate = exchangeRatesMap.get(currency);
            movie.setPrice(movie.getPrice() / rate); //TODO: ask Tolik: why?
        }

        logger.info("Finished getting movie from cache by movieId. Time: {}", System.currentTimeMillis() - startTime);
        return movie;
    }

    @Override
    public void add(Movie movie) {
        movieService.add(movie);
    }

    @Override
    public void update(Movie movie) {
        int key = movie.getId();
        if (movieCache.containsKey(key)){
            movieCache.put(key, new SoftReference<>(movie));
            logger.info("updated movie info in cache, movieId: {}", movie.getId());
        }

        movieService.update(movie);
    }
}
