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
public class MovieCacheService implements MovieService{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;
    private ExchangeRateService exchangeRateService;

    private final Map<Integer, SoftReference<Movie>> movieCache = new ConcurrentHashMap<>();

    @Autowired
    public MovieCacheService(MovieService movieService, ExchangeRateService exchangeRateService) {
        this.movieService = movieService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public Movie getMovieById(int movieId, Currency currency) {
        logger.info("Start getting movie from cache by movieId: {}", movieId);

        Movie movie;
        SoftReference<Movie> movieSoftReference =  movieCache.get(movieId);
        if (movieSoftReference != null){
            movie = movieSoftReference.get();
            if (movie == null){
                movie = movieService.getMovieById(movieId, currency);
                movieCache.put(movieId, new SoftReference<>(movie));
            }
        } else {
            movie = movieService.getMovieById(movieId, currency);
            movieCache.put(movieId, new SoftReference<>(movie));
        }

        if (currency != Currency.UAH) {
            Map<Currency, Double> exchangeRatesMap = exchangeRateService.getExchangeRatesMap();
            double rate = exchangeRatesMap.get(currency);
            movie.setPrice(movie.getPrice() / rate);
        }

        logger.info("Finished getting movie from cache by movieId: {}", movieId);
        return movie;
    }

    @Override
    public void update(Movie movie) {
        movieService.update(movie);

        int key = movie.getId();
        if (movieCache.containsKey(key)){
            movieCache.put(key, new SoftReference<>(movie));
            logger.info("updated movie info in cache, movieId: {}", movie.getId());
        }
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
    public void add(Movie movie) {
        movieService.add(movie);
    }
}
