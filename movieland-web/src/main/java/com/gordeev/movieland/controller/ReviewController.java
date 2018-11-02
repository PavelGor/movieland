package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Review;
import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.ReviewService;
import com.gordeev.movieland.service.SecurityService;
import com.gordeev.movieland.vo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;
    private SecurityService securityService;

    @Autowired
    public ReviewController(ReviewService reviewService, SecurityService securityService) {
        this.reviewService = reviewService;
        this.securityService = securityService;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void add(@RequestBody Review review, @RequestHeader("uuid") String uuid) {
        User user = securityService.getUser(uuid);
        if (user != null && UserRole.USER == user.getUserRole()){
            review.setUser(user);
            reviewService.add(review);
        }
    }
}
