package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.MovieService;
import com.gordeev.movieland.vo.CountryVO;
import com.gordeev.movieland.vo.GenreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, GenreService genreService, CountryService countryService) {
        this.countryService = countryService;
        this.genreService = genreService;
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> movies = movieDao.getAllMovie();

        addGenresAndCountriesToEachMovie(movies);

        return movies;
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        List<Movie> movies = movieDao.getThreeRandomMovie();

        addGenresAndCountriesToEachMovie(movies);

        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenreId(int genreId) {
        List<Integer> moviesIdsByGenre = movieDao.getMoviesIdsByGenre(genreId);
        List<Movie> movies = movieDao.getMoviesByIds(moviesIdsByGenre);

        addGenresAndCountriesToEachMovie(movies);

        return movies;
    }

    private void addGenresAndCountriesToEachMovie(List<Movie> movies) {
        Map<Integer, Genre> genresMap = genreService.getAllGenresMap();
        Map<Integer, Country> countriesMap = countryService.getAllCountriesMap();

        //get moviesIds
        List<Integer> moviesIds = new ArrayList<>();
        for (Movie movie : movies) {
            moviesIds.add(movie.getId());
        }

        //get genres and countries of our movies (Map<movieId, List<genreId>>) and (Map<movieId, List<countryId>>)
        Map<Integer,List<Integer>> moviesGenresIds = getMoviesGenres(moviesIds);
        Map<Integer,List<Integer>> moviesCountriesIds = getMoviesCountries(moviesIds);

        //match and insert genres and countries in each movie in our list
        for (Movie movie : movies) {
            int movieId = movie.getId();
            List<Genre> movieGenres = new ArrayList<>();
            List<Country> movieCountries = new ArrayList<>();

            for (Integer genreId : moviesGenresIds.get(movieId)) {
                movieGenres.add(genresMap.get(genreId));
            }

            for (Integer countryId : moviesCountriesIds.get(movieId)) {
                movieCountries.add(countriesMap.get(countryId));
            }

            movie.setGenres(movieGenres);
            movie.setCountries(movieCountries);
        }
    }

    private Map<Integer, List<Integer>> getMoviesGenres(List<Integer> moviesIds) {
        Map<Integer, List<Integer>> movieGenresMap = new HashMap<>();
        List<GenreVO> genreVOList = movieDao.getMoviesGenres(moviesIds);
        for (GenreVO genreVO : genreVOList) {
            movieGenresMap.put(genreVO.getMovieId(), genreVO.getIntGenres());
        }
        return movieGenresMap;
    }

    private Map<Integer, List<Integer>> getMoviesCountries(List<Integer> moviesIds) {
        Map<Integer, List<Integer>> movieCountriesMap = new HashMap<>();
        List<CountryVO> countryVOList = movieDao.getMoviesCountries(moviesIds);
        for (CountryVO countryVO : countryVOList) {
            movieCountriesMap.put(countryVO.getMovieId(), countryVO.getIntCountries());
        }
        return movieCountriesMap;
    }
}
