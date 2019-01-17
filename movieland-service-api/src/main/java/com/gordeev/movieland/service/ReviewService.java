package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.Review;

import java.util.List;

public interface ReviewService {

    boolean enrich(List<Movie> movies);

    void add(Review review);
}
