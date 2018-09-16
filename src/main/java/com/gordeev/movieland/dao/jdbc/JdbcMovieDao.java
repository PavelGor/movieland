package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.gordeev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMovieDao implements MovieDao {
    private static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
