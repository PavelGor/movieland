package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.vo.MovieToCountiesVo;

import java.util.List;
import java.util.Map;

public interface CountryDao {

    List<Country> getAll();

    List<MovieToCountiesVo> getCountriesForMovies(List<Integer> moviesIds);
}
