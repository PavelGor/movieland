package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovie();

    List<Movie> getThreeRandomMovie();

    List<Genre> getAllGenre();

    List<Movie> getMovieByGenre(int genreId);
}
