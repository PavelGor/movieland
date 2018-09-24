package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll(String column, String direction);

    List<Movie> getThreeRandomMovie();

    List<Movie> getMoviesByGenreId(int genreId);

    List<Movie> getAll();
}
