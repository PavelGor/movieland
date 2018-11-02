package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Country(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
