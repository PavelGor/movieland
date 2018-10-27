package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.RequestParameter;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(RequestParameter requestParameter);

    List<Movie> getThreeRandomMovie();

    List<Movie> getMoviesByIds(List<Integer> genreId);

    List<Movie> getAll();
}