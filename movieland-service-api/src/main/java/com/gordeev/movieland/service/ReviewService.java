package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getByMovieId(int movieId);

    void add(Review review);
}
