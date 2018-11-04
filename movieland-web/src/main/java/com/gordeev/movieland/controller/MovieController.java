package com.gordeev.movieland.controller;

import com.gordeev.movieland.controller.util.CurrencyConverter;
import com.gordeev.movieland.controller.util.SortDirectionConverter;
import com.gordeev.movieland.controller.vo.MovieVO;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.SecurityService;
import com.gordeev.movieland.vo.Currency;
import com.gordeev.movieland.vo.RequestParameter;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.vo.SortDirection;
import com.gordeev.movieland.vo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    protected void sortingBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(SortDirection.class, new SortDirectionConverter());
    }

    @InitBinder
    protected void currencyBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Currency.class, new CurrencyConverter());
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

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    protected Movie getMovieById(@RequestParam(value = "currency", required = false) Currency currency,
                                 @PathVariable int movieId) {
        if (currency == null) {
            currency = Currency.UAH;
        }
        return movieService.getMovieById(movieId, currency);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity add(@RequestBody MovieVO movieVO, @RequestHeader("uuid") String uuid) {
        User user = securityService.getUser(uuid);
        if (user != null && UserRole.ADMIN == user.getUserRole()) {
            Movie movie = transform(movieVO);
            movieService.add(movie);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    protected ResponseEntity update(@RequestBody MovieVO movieVO,
                                    @RequestHeader("uuid") String uuid,
                                    @PathVariable int id) {
        User user = securityService.getUser(uuid);
        if (user != null && UserRole.ADMIN == user.getUserRole()) {
            Movie movie = transform(movieVO);
            movie.setId(id);
            movieService.update(movie);
        }
        return ResponseEntity.ok().build();
    }

    private Movie transform(MovieVO movieVO) {
        Movie movie = new Movie();
        movie.setNameRussian(movieVO.getNameRussian());
        movie.setNameNative(movieVO.getNameNative());
        movie.setYearOfRelease(movieVO.getYearOfRelease());
        movie.setDescription(movieVO.getDescription());
        movie.setRating(movieVO.getRating());
        movie.setPrice(movieVO.getPrice());
        movie.setPicturePath(movieVO.getPicturePath());

        List<Country> countries = new ArrayList<>();
        for (Integer countryId : movieVO.getCountryIds()) {
            Country country = new Country(countryId, "empty");
            countries.add(country);
        }
        movie.setCountries(countries);

        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : movieVO.getGenreIds()) {
            Genre genre = new Genre(genreId, "empty");
            genres.add(genre);
        }
        movie.setGenres(genres);

        return movie;
    }
}
