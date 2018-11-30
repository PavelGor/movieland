package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.Currency;
import com.gordeev.movieland.entity.RequestParameter;

import java.util.List;

public interface MovieService {

    List<Movie> getAll(RequestParameter requestParameter);

    List<Movie> getThreeRandomMovie();

    List<Movie> getMoviesByGenreId(int genreId);

    List<Movie> getAll();

    Movie getMovieById(int movieId, Currency currency);

    void add(Movie movie);

    void update(Movie movie);
}
