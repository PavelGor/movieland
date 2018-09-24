package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(String column, String direction);

    List<Movie> getThreeRandomMovie();

    List<Movie> getMoviesByIds(List<Integer> genreId);

    List<Movie> getAll();
}
