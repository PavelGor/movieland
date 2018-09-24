package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.vo.MovieToGenresVO;

import java.util.List;
import java.util.Map;

public interface GenreDao {

    List<Genre> getAll();

    List<MovieToGenresVO> getGenresForMovies(List<Integer> moviesIds);

    List<Integer> getMoviesIdsByGenreId(int genreId);
}
