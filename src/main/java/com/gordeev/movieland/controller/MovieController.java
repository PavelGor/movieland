package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    @ResponseBody
    protected List<Movie> getAllMovie() {
        List<Movie> movies = movieService.getAllMovie();
        return movies;
    }
}
