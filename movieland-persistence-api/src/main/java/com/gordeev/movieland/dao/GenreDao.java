package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.MovieToGenresVO;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    List<MovieToGenresVO> getGenresForMovies(List<Integer> moviesIds);

    List<Integer> getMoviesIdsByGenreId(int genreId);

    void addToMovie(Movie movie);

    void removeFromMovie(Movie movie);
}
