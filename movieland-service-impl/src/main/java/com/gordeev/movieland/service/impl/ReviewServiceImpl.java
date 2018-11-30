package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.ReviewDao;
import com.gordeev.movieland.entity.Review;
import com.gordeev.movieland.service.ReviewService;
import com.gordeev.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewDao reviewDao;
    private UserService userService;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao, UserService userService) {
        this.reviewDao = reviewDao;
        this.userService = userService;
    }

    @Override
    public List<Review> getByMovieId(int movieId) {
        List<Review> reviews = reviewDao.getByMovieId(movieId);

        Set<Integer> userIds = new HashSet<>();
        for (Review review : reviews) {
            userIds.add(review.getUser().getId());
        }

        Map<Integer, String> nickNamesMap = userService.getNickNamesMap(userIds);

        for (Review review : reviews) {
            int userId = review.getUser().getId();
            review.getUser().setNickname(nickNamesMap.get(userId));
        }

        return reviews;
    }

    @Override
    public void add(Review review) {
        reviewDao.add(review);
    }
}
