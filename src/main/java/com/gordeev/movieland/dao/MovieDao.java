package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovie();

    List<Genre> getAllGenre();
}
