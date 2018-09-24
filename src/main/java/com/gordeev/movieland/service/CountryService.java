package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface CountryService {
    List<Country> getAll();

    void enrich(List<Movie> movies);
}
