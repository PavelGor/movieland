package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getByMovieId(int movieId);
}
