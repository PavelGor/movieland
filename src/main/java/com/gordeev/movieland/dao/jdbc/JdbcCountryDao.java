package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieCountriesExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.vo.MovieToCountiesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao{
    private static final String GET_ALL_COUNTRY_SQL = "SELECT * FROM country";
    private static final String GET_MOVIES_VO_BY_IDS_SQL = "SELECT mc.movie_id, c.id, c.name FROM movie_country as mc LEFT JOIN country as c ON mc.country_id = c.id WHERE mc.movie_id IN (:moviesIds)";

    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private static final ResultSetExtractor<List<MovieToCountiesVo>> MOVIE_COUNTRIES_EXTRACTOR = new MovieCountriesExtractor();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public JdbcCountryDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Country> getAll() {
        logger.info("Start processing query to get all countries");
        long startTime = System.currentTimeMillis();

        List<Country> countries = jdbcTemplate.query(GET_ALL_COUNTRY_SQL, COUNTRY_ROW_MAPPER);

        logger.info("Finish processing query to get all countries. It took {} ms", System.currentTimeMillis() - startTime);
        return countries;
    }

    @Override
    public List<MovieToCountiesVo> getCountriesForMovies(List<Integer> moviesIds) {
        logger.info("Start processing query to get countries for movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<MovieToCountiesVo> moviesVO = namedParameterJdbcTemplate.query(GET_MOVIES_VO_BY_IDS_SQL, parameters, MOVIE_COUNTRIES_EXTRACTOR);

        logger.info("Finish processing query to get countries for movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return moviesVO;
    }
}
