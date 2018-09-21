package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Genre;

import java.util.List;
import java.util.Map;

public interface GenreService {
    List<Genre> getAllGenre();

    Map<Integer,Genre> getAllGenresMap();
}
