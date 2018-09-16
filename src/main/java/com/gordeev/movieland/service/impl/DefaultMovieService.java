package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultMovieService implements MovieService{
    private MovieDao movieDao;

    @Autowired
    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
