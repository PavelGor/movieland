package com.gordeev.movieland.cache;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Component
public class GenreCache {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreDao genreDao;

    private List<Genre> genres;

    public GenreCache(GenreDao genreDao) {
        this.genreDao = genreDao;
        setAllGenre();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setAllGenre();
            }
        };
        timer.schedule(task, new Date(), TimeUnit.HOURS.toHours(14400000));
    }

    private void setAllGenre() {
            this.genres = genreDao.getAllGenre();
            logger.info("Update all genres in cache from dao");
    }

    public List<Genre> getAllGenre() {
        logger.info("Get all genres from cache");
        return genres;
    }
}
