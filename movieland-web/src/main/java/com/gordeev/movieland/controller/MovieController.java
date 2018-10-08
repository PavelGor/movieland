package com.gordeev.movieland.controller;

import com.gordeev.movieland.controller.util.SortDirectionConverter;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.RequestParameter;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.vo.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @InitBinder
    protected void sortingBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(SortDirection.class, new SortDirectionConverter());
    }

    @RequestMapping(method = RequestMethod.GET)
    protected List<Movie> getAllMovie(@RequestParam(value = "rating", required = false) SortDirection ratingDirection,
                                      @RequestParam(value = "price", required = false) SortDirection priceDirection) {
        if (ratingDirection == SortDirection.DESC) {
            return movieService.getAll(new RequestParameter("rating", ratingDirection));
        }
        if (priceDirection == SortDirection.DESC || priceDirection == SortDirection.ASC) {
            return movieService.getAll(new RequestParameter("price", priceDirection));
        }
        return movieService.getAll();
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    protected List<Movie> getThreeRandomMovie() {
        return movieService.getThreeRandomMovie();
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    protected List<Movie> getMoviesByGenreId(@PathVariable int genreId) {
        return movieService.getMoviesByGenreId(genreId);
    }
}
