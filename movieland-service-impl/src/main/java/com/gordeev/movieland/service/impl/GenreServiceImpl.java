package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.vo.MovieToGenresVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public boolean enrich(List<Movie> movies) {
        List<Integer> moviesIds = new ArrayList<>();
        for (Movie movie : movies) {
            moviesIds.add(movie.getId());
        }

        if(Thread.currentThread().isInterrupted()) { return false; }

        List<MovieToGenresVO> genresForMoviesVO = genreDao.getGenresForMovies(moviesIds);
        Map<Integer, List<Genre>> genresForMoviesMap = new HashMap<>();
        for (MovieToGenresVO movieToGenresVO : genresForMoviesVO) {
            genresForMoviesMap.put(movieToGenresVO.getMovieId(), movieToGenresVO.getGenres());
        }

        if(Thread.currentThread().isInterrupted()) { return false; }

        for (Movie movie : movies) {
            int movieId = movie.getId();
            movie.setGenres(genresForMoviesMap.get(movieId));
        }
        return true;
    }

    @Override
    public List<Integer> getMoviesIdsByGenreId(int genreId) {
        return genreDao.getMoviesIdsByGenreId(genreId);
    }

    @Override
    public void addToMovie(Movie movie) {
        genreDao.addToMovie(movie);
    }

    @Override
    public void removeFromMovie(Movie movie) {
        genreDao.removeFromMovie(movie);
    }


}
