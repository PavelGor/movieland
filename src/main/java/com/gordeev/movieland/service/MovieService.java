package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovie();

    List<Movie> getThreeRandomMovie();

}
