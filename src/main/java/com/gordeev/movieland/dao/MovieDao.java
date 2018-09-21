package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.CountryVO;
import com.gordeev.movieland.vo.GenreVO;

import java.util.List;
import java.util.Map;

public interface MovieDao {

    List<Movie> getAllMovie();

    List<Movie> getThreeRandomMovie();

    List<Movie> getMoviesByIds(List<Integer> genreId);

    List<Integer> getMoviesIdsByGenre(int genreId);

    List<GenreVO> getMoviesGenres(List<Integer> moviesIds);

    List<CountryVO> getMoviesCountries(List<Integer> moviesIds);
}
