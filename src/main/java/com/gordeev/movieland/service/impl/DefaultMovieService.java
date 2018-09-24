package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, GenreService genreService, CountryService countryService) {
        this.countryService = countryService;
        this.genreService = genreService;
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll(String column, String direction) {
        List<Movie> movies = movieDao.getAll(column, direction);

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

    private void enrich(List<Movie> movies) {
        genreService.enrich(movies);
        countryService.enrich(movies);
    }
}
