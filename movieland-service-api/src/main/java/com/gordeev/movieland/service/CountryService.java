package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;

import java.util.List;

public interface CountryService {
    List<Country> getAll();

    void enrich(List<Movie> movies);

    void addToMovie(Movie movie);

    void removeFromMovie(Movie movie);
}
