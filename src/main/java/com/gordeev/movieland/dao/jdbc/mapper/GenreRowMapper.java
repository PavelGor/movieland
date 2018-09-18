package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();

        genre.setId(resultSet.getInt("id"));
        genre.setName(resultSet.getString("name"));

        return genre;
    }
}
