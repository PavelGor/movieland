package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.vo.MovieToCountiesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao; //TODO: do it with cache - as  a genres

    @Autowired
    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public void enrich(List<Movie> movies) {
        List<Integer> moviesIds = new ArrayList<>();
        for (Movie movie : movies) {
            moviesIds.add(movie.getId());
        }

        List<MovieToCountiesVo>  countriesForMoviesVO = countryDao.getCountriesForMovies(moviesIds);
        Map<Integer, List<Country>>  countriesForMoviesMap = new HashMap<>();
        for (MovieToCountiesVo movieToCountiesVo : countriesForMoviesVO) {
            countriesForMoviesMap.put(movieToCountiesVo.getMovieId(), movieToCountiesVo.getCountries());
        }

        for (Movie movie : movies) {
            int movieId = movie.getId();
            movie.setCountries(countriesForMoviesMap.get(movieId));
        }
    }
}
