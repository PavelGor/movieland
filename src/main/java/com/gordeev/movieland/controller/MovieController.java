package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/movie")
    @ResponseBody
    protected List<Movie> getAllMovie() {
        return movieService.getAllMovie();
    }

    @RequestMapping("/movie/random")
    @ResponseBody
    protected List<Movie> getThreeRandomMovie() {
        return movieService.getThreeRandomMovie();
    }

    @RequestMapping("/genre")
    @ResponseBody
    protected List<Genre> getAllGenre() {
        return movieService.getAllGenre();
    }

    @RequestMapping("/movie/genre/{genreId}")
    @ResponseBody
    protected List<Movie> getMovieByGenre(@PathVariable int genreId) {
        return movieService.getMovieByGenre(genreId);
    }
}
