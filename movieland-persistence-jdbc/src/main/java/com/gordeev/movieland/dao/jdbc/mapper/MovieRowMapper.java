package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setId(resultSet.getInt("id"));
        movie.setNameRussian(resultSet.getString("name_ru"));
        movie.setNameNative(resultSet.getString("name_eng"));
        movie.setYearOfRelease(resultSet.getInt("year_release"));
        movie.setDescription(resultSet.getString("description"));
        movie.setRating(Math.round(resultSet.getDouble("rating") * 100.0) / 100.0);
        movie.setPrice(Math.round(resultSet.getDouble("price") * 100.0) / 100.0);
        movie.setPicturePath(resultSet.getString("poster"));

        return movie;
    }
}
