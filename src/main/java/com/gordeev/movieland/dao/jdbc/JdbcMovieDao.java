package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieCountriesExtractor;
import com.gordeev.movieland.dao.jdbc.extractor.MovieGenresExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.CountryVO;
import com.gordeev.movieland.vo.GenreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final ResultSetExtractor<List<GenreVO>>  MOVIE_GENRES_EXTRACTOR = new MovieGenresExtractor();
    private static final ResultSetExtractor<List<CountryVO>>  MOVIE_COUNTRIES_EXTRACTOR = new MovieCountriesExtractor();
    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String GET_ALL_MOVIE_SQL = "SELECT * FROM movie";
    private static final String GET_THREE_RANDOM_MOVIE_SQL = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3";
    private static final String GET_MOVIES_BY_IDS_SQL = "SELECT * FROM movie WHERE id IN (:moviesIds)";
    private static final String GET_GENRES_OF_MOVIES_SQL = "SELECT * FROM movie_genre WHERE movie_id IN (:moviesIds)";
    private static final String GET_COUNTRIES_OF_MOVIES_SQL = "SELECT * FROM movie_country WHERE movie_id IN (:moviesIds)";
    private static final String GET_MOVIES_IDS_BY_GENRE_SQL = "SELECT movie_id FROM movie_genre WHERE genre_id = ?";

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Movie> getAllMovie() {
        logger.info("Start processing query to get all movies");
        long startTime = System.currentTimeMillis();

        List<Movie> movies = jdbcTemplate.query(GET_ALL_MOVIE_SQL, MOVIE_ROW_MAPPER);

        logger.info("Finish processing query to get all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }


    @Override
    public List<Integer> getMoviesIdsByGenre(int genreId) {
        logger.info("Start processing query to get movieIds by genre");
        long startTime = System.currentTimeMillis();

        List<Integer> movieIds = jdbcTemplate.query(GET_MOVIES_IDS_BY_GENRE_SQL, new SingleColumnRowMapper<>(), genreId);

        logger.info("Finish processing query to get movieIds by genre. It took {} ms", System.currentTimeMillis() - startTime);
        return movieIds;
    }

    @Override
    public List<GenreVO> getMoviesGenres(List<Integer> moviesIds) {
        logger.info("Start processing query to get genresVO of movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<GenreVO> moviesGenres = namedParameterJdbcTemplate.query(GET_GENRES_OF_MOVIES_SQL, parameters, MOVIE_GENRES_EXTRACTOR);

        logger.info("Finish processing query to get genresVO of movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return moviesGenres;
    }

    @Override
    public List<CountryVO> getMoviesCountries(List<Integer> moviesIds) {
        logger.info("Start processing query to get countriesVO of movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<CountryVO> moviesCountries = namedParameterJdbcTemplate.query(GET_COUNTRIES_OF_MOVIES_SQL, parameters, MOVIE_COUNTRIES_EXTRACTOR);

        logger.info("Finish processing query to get countriesVO of movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return moviesCountries;
    }

    @Override
    public List<Movie> getThreeRandomMovie() {
        logger.info("Start processing query to get 3 random movies");
        long startTime = System.currentTimeMillis();

        List<Movie> movies = jdbcTemplate.query(GET_THREE_RANDOM_MOVIE_SQL, MOVIE_ROW_MAPPER);

        logger.info("Finish processing query to get 3 random movies. It took {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Movie> getMoviesByIds(List<Integer> moviesIds) {
        logger.info("Start processing query to get movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<Movie> movies = namedParameterJdbcTemplate.query(GET_MOVIES_BY_IDS_SQL, parameters, MOVIE_ROW_MAPPER);

        logger.info("Finish processing query to get movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return movies;
    }
}
