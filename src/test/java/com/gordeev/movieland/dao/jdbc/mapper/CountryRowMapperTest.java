package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Country;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {

    private static final int ID = 4;
    private static final String NAME = "США";

    @Test
    public void mapRow() throws SQLException {
        CountryRowMapper countryRowMapper = new CountryRowMapper();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);

        Country country = countryRowMapper.mapRow(resultSet,1);

        assertNotNull(country);
        assertEquals(ID, country.getId());
        assertEquals(NAME, country.getName());
    }
}