package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.MovieToCountiesVo;

import java.util.List;

public interface CountryDao {

    List<Country> getAll();

    List<MovieToCountiesVo> getCountriesForMovies(List<Integer> moviesIds);

    void addToMovie(Movie movie);

    void removeFromMovie(Movie movie);
}
