package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
public class DefaultMovieServiceTest {
    private List<Movie> expectedMovies;

    @Autowired
    private DefaultMovieService movieService;

    @Before
    public void setUp(){
        //List of movies setUp
        expectedMovies = new ArrayList<>();
        Movie firstMovie = new Movie();
        Movie secondMovie = new Movie();
        expectedMovies.add(firstMovie);
        expectedMovies.add(secondMovie);

        //First movie setUp
        firstMovie.setId(1);
        firstMovie.setNameRussian("Побег из Шоушенка");
        firstMovie.setNameNative("The Shawshank Redemption");
        firstMovie.setYearOfRelease(1994);
        firstMovie.setDescription("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.");
        firstMovie.setRating(8.9);
        firstMovie.setPrice(123.45);
        firstMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg");

        //Second movie setUp
        secondMovie.setId(6);
        secondMovie.setNameRussian("Начало");
        secondMovie.setNameNative("Inception");
        secondMovie.setYearOfRelease(2010);
        secondMovie.setDescription("Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.");
        secondMovie.setRating(8.6);
        secondMovie.setPrice(130.0);
        secondMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg");
    }

    @Test
    public void testGetAllMovie() {
        List<Movie> actualMovies = movieService.getAllMovie();

        assertTrue(actualMovies.size() == 25);

        assertEqualsList(actualMovies);
    }

    @Test
    public void testGetThreeRandomMovie() {
        List<Movie> actualMovies = movieService.getThreeRandomMovie();

        assertTrue(actualMovies.size() == 3);
    }



    @Test
    public void testGetMovieByGenreId() {
        int genreId = 1; //it is drama
        List<Movie> actualMovies = movieService.getMoviesByGenreId(genreId);

        assertTrue(actualMovies.size() == 16);

        assertEqualsList(actualMovies);
    }

    private void assertEqualsList(List<Movie> actualMovies) {
        Movie expectedFirstMovie = expectedMovies.get(0);
        Movie expectedSecondMovie = expectedMovies.get(1);
        Movie actualFirstMovie = actualMovies.get(0);
        Movie actualSecondMovie = actualMovies.get(5);

        assertEquals(expectedFirstMovie.getId(), actualFirstMovie.getId());
        assertEquals(expectedSecondMovie.getId(), actualSecondMovie.getId());

        assertEquals(expectedFirstMovie.getNameRussian(), actualFirstMovie.getNameRussian());
        assertEquals(expectedSecondMovie.getNameRussian(), actualSecondMovie.getNameRussian());

        assertEquals(expectedFirstMovie.getNameNative(), actualFirstMovie.getNameNative());
        assertEquals(expectedSecondMovie.getNameNative(), actualSecondMovie.getNameNative());

        assertEquals(expectedFirstMovie.getYearOfRelease(), actualFirstMovie.getYearOfRelease());
        assertEquals(expectedSecondMovie.getYearOfRelease(), actualSecondMovie.getYearOfRelease());

        assertEquals(expectedFirstMovie.getDescription(), actualFirstMovie.getDescription());
        assertEquals(expectedSecondMovie.getDescription(), actualSecondMovie.getDescription());

        assertEquals(expectedFirstMovie.getRating(), actualFirstMovie.getRating(), 0.001);
        assertEquals(expectedSecondMovie.getRating(), actualSecondMovie.getRating(), 0.001);

        assertEquals(expectedFirstMovie.getPrice(), actualFirstMovie.getPrice(), 0.001);
        assertEquals(expectedSecondMovie.getPrice(), actualSecondMovie.getPrice(), 0.001);

        assertEquals(expectedFirstMovie.getPicturePath(), actualFirstMovie.getPicturePath());
        assertEquals(expectedSecondMovie.getPicturePath(), actualSecondMovie.getPicturePath());

        assertTrue(actualFirstMovie.getCountries().size() == 1);
        assertTrue(actualFirstMovie.getGenres().size() == 2);
        assertTrue(actualSecondMovie.getCountries().size() == 2);
        assertTrue(actualSecondMovie.getGenres().size() == 5);
    }
}