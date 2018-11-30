package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.vo.MovieToCountiesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryServiceImpl implements CountryService {
    private CountryDao countryDao;

    @Autowired
    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public void enrich(List<Movie> movies) {
        List<Integer> moviesIds = new ArrayList<>();
        for (Movie movie : movies) {
            moviesIds.add(movie.getId());
        }

        List<MovieToCountiesVO> countriesForMoviesVO = countryDao.getCountriesForMovies(moviesIds);
        Map<Integer, List<Country>> countriesForMoviesMap = new HashMap<>();
        for (MovieToCountiesVO movieToCountiesVO : countriesForMoviesVO) {
            countriesForMoviesMap.put(movieToCountiesVO.getMovieId(), movieToCountiesVO.getCountries());
        }

        for (Movie movie : movies) {
            int movieId = movie.getId();
            movie.setCountries(countriesForMoviesMap.get(movieId));
        }
    }

    @Override
    public void addToMovie(Movie movie) {
        countryDao.addToMovie(movie);
    }

    @Override
    public void removeFromMovie(Movie movie) {
        countryDao.removeFromMovie(movie);
    }
}
