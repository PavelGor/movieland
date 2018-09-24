package com.gordeev.movieland.dao.cache;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.vo.MovieToGenresVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Primary
@Repository
public class GenreCache implements GenreDao{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GenreDao genreDao;

    private List<Genre> genres;

    @Autowired
    public GenreCache(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    @PostConstruct
    private void invalidate() {
            this.genres = genreDao.getAll();
            logger.info("Update all genres in cache from dao");
    }

    public List<Genre> getAll() {
        logger.info("Get all genres from cache");
        return genres;
    }

    @Override
    public List<MovieToGenresVO> getGenresForMovies(List<Integer> moviesIds) {
        return genreDao.getGenresForMovies(moviesIds);
    }

    @Override
    public List<Integer> getMoviesIdsByGenreId(int genreId) {
        return genreDao.getMoviesIdsByGenreId(genreId);
    }
}
