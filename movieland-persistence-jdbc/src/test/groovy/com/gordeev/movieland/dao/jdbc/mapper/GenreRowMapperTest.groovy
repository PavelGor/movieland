package com.gordeev.movieland.dao.jdbc.mapper

import com.gordeev.movieland.entity.Genre
import org.junit.Test

import java.sql.ResultSet
import java.sql.SQLException

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class GenreRowMapperTest {

    private static final int ID = 1
    private static final String NAME = "fantasy"

    @Test
    void testMapRow() throws SQLException {

        GenreRowMapper genreRowMapper = new GenreRowMapper()

        ResultSet resultSet = mock(ResultSet.class)

        when(resultSet.getInt("id")).thenReturn(ID)
        when(resultSet.getString("name")).thenReturn(NAME)

        Genre genre = genreRowMapper.mapRow(resultSet,1)

        assertNotNull(genre)
        assertEquals(ID, genre.getId())
        assertEquals(NAME, genre.getName())

    }
}