package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {

        return null;
    }
}
