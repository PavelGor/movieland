package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.gordeev.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_GENRE_SQL = "SELECT * FROM genre";

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        logger.info("Start processing query to get all genres");
        long startTime = System.currentTimeMillis();

        List<Genre> genres = jdbcTemplate.query(GET_ALL_GENRE_SQL, GENRE_ROW_MAPPER);

        logger.info("Finish processing query to get all genres. It took {} ms", System.currentTimeMillis() - startTime);
        return genres;
    }
}
