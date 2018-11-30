package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieCountriesExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.MovieToCountiesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao{
    private static final String GET_ALL_COUNTRY_SQL = "SELECT * FROM country";
    private static final String GET_MOVIES_VO_BY_IDS_SQL = "SELECT mc.movie_id, c.id, c.name FROM movie_country as mc LEFT JOIN country as c ON mc.country_id = c.id WHERE mc.movie_id IN (:moviesIds)";
    private static final String INSERT_COUNTRYID_TO_MOVIE_SQL = "INSERT INTO movie_country (movie_id, country_id) VALUES ( :movieId, :countryId)";
    private static final String DELETE_COUNTRIES_FROM_MOVIE_SQL = "DELETE FROM movie_country WHERE movie_id = :movieId";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private static final ResultSetExtractor<List<MovieToCountiesVO>> MOVIE_COUNTRIES_EXTRACTOR = new MovieCountriesExtractor();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public JdbcCountryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Country> getAll() {
        logger.info("Start processing query to get all countries");
        long startTime = System.currentTimeMillis();

        List<Country> countries = namedParameterJdbcTemplate.query(GET_ALL_COUNTRY_SQL, COUNTRY_ROW_MAPPER);

        logger.info("Finish processing query to get all countries. It took {} ms", System.currentTimeMillis() - startTime);
        return countries;
    }

    @Override
    public List<MovieToCountiesVO> getCountriesForMovies(List<Integer> moviesIds) {
        logger.info("Start processing query to get countries for movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<MovieToCountiesVO> moviesVO = namedParameterJdbcTemplate.query(GET_MOVIES_VO_BY_IDS_SQL, parameters, MOVIE_COUNTRIES_EXTRACTOR);

        logger.info("Finish processing query to get countries for movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return moviesVO;
    }

    @Override
    public void addToMovie(Movie movie) {
        int movieId = movie.getId();
        List<Country> countries = movie.getCountries();
        logger.info("Start processing query to add countries to movie: ", movieId);
        long startTime = System.currentTimeMillis();

        List<MapSqlParameterSource> batchArgs = new ArrayList<>();

        for (Country country : countries) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("movieId", movieId);
            parameters.addValue("countryId", country.getId());
            batchArgs.add(parameters);
        }

        namedParameterJdbcTemplate.batchUpdate(INSERT_COUNTRYID_TO_MOVIE_SQL, batchArgs.toArray(new MapSqlParameterSource[countries.size()]));

        logger.info("Finish processing query to add countries to movie: {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
    }

    @Override
    public void removeFromMovie(Movie movie) {
        logger.info("Start processing query to remove countries from movie {}", movie.getId());
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieId", movie.getId());

        namedParameterJdbcTemplate.update(DELETE_COUNTRIES_FROM_MOVIE_SQL, parameters);

        logger.info("Finish processing query to remove from countries. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
