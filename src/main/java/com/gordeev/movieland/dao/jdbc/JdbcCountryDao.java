package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.CountryDao;
import com.gordeev.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.gordeev.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_COUNTRY_SQL = "SELECT * FROM country";

    public JdbcCountryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> getAllCountries() {
        logger.info("Start processing query to get all countries");
        long startTime = System.currentTimeMillis();

        List<Country> countries = jdbcTemplate.query(GET_ALL_COUNTRY_SQL, COUNTRY_ROW_MAPPER);

        logger.info("Finish processing query to get all countries. It took {} ms", System.currentTimeMillis() - startTime);
        return countries;
    }
}
