package com.gordeev.movieland.controller.util;

import com.gordeev.movieland.controller.vo.MovieVO;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static Movie transform(MovieVO movieVO) {
        Movie movie = new Movie();
        movie.setNameRussian(movieVO.getNameRussian());
        movie.setNameNative(movieVO.getNameNative());
        movie.setYearOfRelease(movieVO.getYearOfRelease());
        movie.setDescription(movieVO.getDescription());
        movie.setRating(movieVO.getRating());
        movie.setPrice(movieVO.getPrice());
        movie.setPicturePath(movieVO.getPicturePath());

        List<Country> countries = new ArrayList<>();
        for (Integer countryId : movieVO.getCountries()) {
            Country country = new Country(countryId, "empty");
            countries.add(country);
        }
        movie.setCountries(countries);

        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : movieVO.getGenres()) {
            Genre genre = new Genre(genreId, "empty");
            genres.add(genre);
        }
        movie.setGenres(genres);

        return movie;
    }
}
