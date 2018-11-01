package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.RequestParameter;
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
    private static final String GET_ALL_ORDERED_MOVIE_SQL = "SELECT * FROM movie ORDER BY ";
    private static final String GET_ALL_MOVIE_SQL = "SELECT * FROM movie";
    private static final String GET_THREE_RANDOM_MOVIE_SQL = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3";
    private static final String GET_MOVIES_BY_IDS_SQL = "SELECT * FROM movie WHERE id IN (:moviesIds)";

    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll(RequestParameter requestParameter) {
        logger.info("Start processing query to get all movies ordered by column: {} and direction is: {}", requestParameter.getFieldName(), requestParameter.getSortDirection());
        long startTime = System.currentTimeMillis();
        //TODO: Tests!!! create query generator
        String sql = GET_ALL_ORDERED_MOVIE_SQL + " " + requestParameter.getFieldName() + " " + requestParameter.getSortDirection().getName();

        List<Movie> movies = jdbcTemplate.query(sql, MOVIE_ROW_MAPPER);

        logger.info("Finish processing query to get all movies ordered by column: {} and direction is: {}. It took {} ms", requestParameter.getFieldName(), requestParameter.getSortDirection(), System.currentTimeMillis() - startTime);
        return movies;
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

    @Override
    public List<Movie> getAll() {
        logger.info("Start processing query to get all movies");
        long startTime = System.currentTimeMillis();

        List<Movie> movies = jdbcTemplate.query(GET_ALL_MOVIE_SQL, MOVIE_ROW_MAPPER);

        logger.info("Finish processing query to get all movies. It took {} ms", System.currentTimeMillis() - startTime);
        return movies;
    }
}
