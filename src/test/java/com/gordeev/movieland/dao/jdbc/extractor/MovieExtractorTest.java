package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.entity.Movie;
import org.junit.Test;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieExtractorTest {

    private static final int id = 1;
    private static final String nameRussian = "Побег из Шоушенка";
    private static final String nameNative = "The Shawshank Redemption";
    private static final int yearOfRelease = 1994;
    private static final String description = "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.";
    private static final double rating = 8.9;
    private static final double price = 123.45;
    private static final String picturePath = "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg";
    private static final int countryId = 4;
    private static final  String country = "США";
    private static final  String genryOne = "драма";
    private static final  String genryTwo = "криминал";


    @Test
    public void testExtractData() throws SQLException {
        ResultSetExtractor<List<Movie>> MOVIE_EXTRACTOR = new MovieExtractor();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getInt("id")).thenReturn(id);
        when(resultSet.getString("name_ru")).thenReturn(nameRussian);
        when(resultSet.getString("name_eng")).thenReturn(nameNative);
        when(resultSet.getInt("year_release")).thenReturn(yearOfRelease);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getDouble("rating")).thenReturn(rating);
        when(resultSet.getDouble("price")).thenReturn(price);
        when(resultSet.getString("poster")).thenReturn(picturePath);
        when(resultSet.getInt("country_id")).thenReturn(countryId);
        when(resultSet.getString("country_name")).thenReturn(country);
        when(resultSet.getString("genre_name")).thenReturn(genryOne).thenReturn(genryTwo);


        List<Movie> movies = MOVIE_EXTRACTOR.extractData(resultSet);

        Movie expectedMovie = movies.get(0);

        assertTrue(movies.size() == 1);
        assertEquals(id, expectedMovie.getId());
        assertEquals(nameRussian, expectedMovie.getNameRussian());
        assertEquals(nameNative, expectedMovie.getNameNative());
        assertEquals(yearOfRelease, expectedMovie.getYearOfRelease());
        assertEquals(description, expectedMovie.getDescription());
        assertEquals(rating, expectedMovie.getRating(), 0.001);
        assertEquals(price, expectedMovie.getPrice(), 0.001);
        assertEquals(picturePath, expectedMovie.getPicturePath());
        assertEquals(country, expectedMovie.getCountries().get(0));
//        assertEquals(genryOne, expectedMovie.getGenres().get(0));
//        assertEquals(genryTwo, expectedMovie.getGenres().get(1));
    }
}