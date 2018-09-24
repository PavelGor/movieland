package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface GenreService {
    List<Genre> getAll();

    void enrich(List<Movie> movies);

    List<Integer> getMoviesIdsByGenreId(int genreId);
}
