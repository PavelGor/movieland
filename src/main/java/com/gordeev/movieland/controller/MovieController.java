package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/movie*", method = RequestMethod.GET)
    @ResponseBody
    protected List<Movie> getAllMovie(@RequestParam(value = "rating", required = false) String ratingDirection,
                                      @RequestParam(value = "price", required = false) String priceDirection) {
        if (ratingDirection != null) {
            return movieService.getAll("rating", ratingDirection);
        }
        if (priceDirection != null) {
            return movieService.getAll("price", priceDirection);
        }
        return movieService.getAll();
    }

    @RequestMapping(value = "/movie/random", method = RequestMethod.GET)
    @ResponseBody
    protected List<Movie> getThreeRandomMovie() {
        return movieService.getThreeRandomMovie();
    }

    @RequestMapping(value = "/movie/genre/{genreId}", method = RequestMethod.GET)
    @ResponseBody
    protected List<Movie> getMoviesByGenreId(@PathVariable int genreId) {
        return movieService.getMoviesByGenreId(genreId);
    }
}
