package com.gordeev.movieland.dao.cache;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.MovieToCountiesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class CountryCache implements CountryDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CountryDao countryDao;

    private volatile List<Country> countries;

    @Autowired
    public CountryCache(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${cache.updateRate}", initialDelayString = "${cache.updateRate}")
    private void invalidate() {
        this.countries = countryDao.getAll();
        logger.info("Update all countries in cache from dao");
    }

    @Override
    public List<Country> getAll() {
        logger.info("Get all countries from cache");
        return new ArrayList<>(countries);
    }

    @Override
    public List<MovieToCountiesVO> getCountriesForMovies(List<Integer> moviesIds) {
        return countryDao.getCountriesForMovies(moviesIds);
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
