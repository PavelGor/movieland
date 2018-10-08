package com.gordeev.movieland.dao.jdbc.mapper

import com.gordeev.movieland.entity.Movie
import org.junit.Test

import java.sql.ResultSet
import java.sql.SQLException

import static org.junit.Assert.*
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieRowMapperTest {

    private static final int id = 1
    private static final String nameRussian = "Побег из Шоушенка"
    private static final String nameNative = "The Shawshank Redemption"
    private static final int yearOfRelease = 1994
    private static final String description = "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения."
    private static final double rating = 8.9
    private static final double price = 123.45
    private static final String picturePath = "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg"

    @Test
    void testMapRow() throws SQLException {
        MovieRowMapper movieRowMapper = new MovieRowMapper()

        ResultSet resultSet = mock(ResultSet.class)

        when(resultSet.next()).thenReturn(true).thenReturn(false)

        when(resultSet.getInt("id")).thenReturn(id)
        when(resultSet.getString("name_ru")).thenReturn(nameRussian)
        when(resultSet.getString("name_eng")).thenReturn(nameNative)
        when(resultSet.getInt("year_release")).thenReturn(yearOfRelease)
        when(resultSet.getString("description")).thenReturn(description)
        when(resultSet.getDouble("rating")).thenReturn(rating)
        when(resultSet.getDouble("price")).thenReturn(price)
        when(resultSet.getString("poster")).thenReturn(picturePath)

        Movie expectedMovie = movieRowMapper.mapRow(resultSet,1)

        assertEquals(id, expectedMovie.getId())
        assertEquals(nameRussian, expectedMovie.getNameRussian())
        assertEquals(nameNative, expectedMovie.getNameNative())
        assertEquals(yearOfRelease, expectedMovie.getYearOfRelease())
        assertEquals(description, expectedMovie.getDescription())
        assertEquals(rating, expectedMovie.getRating(), 0.001 as double)
        assertEquals(price, expectedMovie.getPrice(), 0.001 as double)
        assertEquals(picturePath, expectedMovie.getPicturePath())
    }
}
