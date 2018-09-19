package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DefaultMovieService implements MovieService{
    private MovieDao movieDao;
    private Random randomGenerator = new Random();

    @Autowired
    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovie() {
        return movieDao.getAllMovie();
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        return movieDao.getThreeRandomMovie();
    }
}
