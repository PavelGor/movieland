package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Genre;
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
        List<Movie> allMovies = movieDao.getAllMovie();
        List<Movie> threeRandomMovie = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int index = randomGenerator.nextInt(allMovies.size());
            threeRandomMovie.add(allMovies.get(index));
            allMovies.remove(index);
        }

        return threeRandomMovie;
    }

    @Override
    public List<Genre> getAllGenre() {
        return movieDao.getAllGenre();
    }
}
