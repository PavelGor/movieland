package com.gordeev.movieland.controller;

import com.gordeev.movieland.controller.util.CurrencyPropertyEditor;
import com.gordeev.movieland.controller.util.SortDirectionPropertyEditor;
import com.gordeev.movieland.controller.util.Util;
import com.gordeev.movieland.controller.vo.MovieVO;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.SecurityService;
import com.gordeev.movieland.entity.Currency;
import com.gordeev.movieland.entity.RequestParameter;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.entity.SortDirection;
import com.gordeev.movieland.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;
    private SecurityService securityService;

    @Autowired
    public MovieController(MovieService movieService, SecurityService securityService) {
        this.movieService = movieService;
        this.securityService = securityService;
    }

    @InitBinder
    public void sortingBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(SortDirection.class, new SortDirectionPropertyEditor());
    }

    @InitBinder
    public void currencyBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Currency.class, new CurrencyPropertyEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> getAllMovie(@RequestParam(value = "rating", required = false) SortDirection ratingDirection,
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
    public List<Movie> getThreeRandomMovie() {
        return movieService.getThreeRandomMovie();
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    public List<Movie> getMoviesByGenreId(@PathVariable int genreId) {
        return movieService.getMoviesByGenreId(genreId);
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public Movie getMovieById(@RequestParam(value = "currency", required = false) Currency currency,
                                 @PathVariable int movieId) {
        if (currency == null) {
            currency = Currency.UAH;
        }
        return movieService.getMovieById(movieId, currency);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody MovieVO movieVO, @RequestHeader("uuid") String uuid) {
        User user = securityService.getUser(uuid);
        if (user != null && UserRole.ADMIN == user.getUserRole()) {
            Movie movie = Util.transform(movieVO);
            movieService.add(movie);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody MovieVO movieVO,
                                    @RequestHeader("uuid") String uuid,
                                    @PathVariable int id) {
        User user = securityService.getUser(uuid);
        if (user != null && UserRole.ADMIN == user.getUserRole()) {
            Movie movie = Util.transform(movieVO);
            movie.setId(id);
            movieService.update(movie);
        }
        return ResponseEntity.ok().build();
    }


}
