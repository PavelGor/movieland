package com.gordeev.movieland.dao.jdbc

import com.gordeev.movieland.dao.MovieDao
import com.gordeev.movieland.entity.Movie
import com.gordeev.movieland.vo.RequestParameter
import com.gordeev.movieland.vo.SortDirection
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
class JdbcMovieDaoTest extends GroovyTestCase {
    private List<Movie> expectedMovies

    @Autowired
    private MovieDao movieDao

    @Before
    void setUp(){
        //List of movies setUp
        expectedMovies = new ArrayList<>()
        Movie firstMovie = new Movie()
        Movie secondMovie = new Movie()
        expectedMovies.add(firstMovie)
        expectedMovies.add(secondMovie)

        //First movie setUp
        firstMovie.setId(1)
        firstMovie.setNameRussian("Побег из Шоушенка")
        firstMovie.setNameNative("The Shawshank Redemption")
        firstMovie.setYearOfRelease(1994)
        firstMovie.setDescription("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.")
        firstMovie.setRating(8.9 as double)
        firstMovie.setPrice(123.45 as double)
        firstMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")

        //Second movie setUp
        secondMovie.setId(6)
        secondMovie.setNameRussian("Начало")
        secondMovie.setNameNative("Inception")
        secondMovie.setYearOfRelease(2010)
        secondMovie.setDescription("Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.")
        secondMovie.setRating(8.6 as double)
        secondMovie.setPrice(130.0 as double)
        secondMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg")
    }

    @Test
    void testGetAllMovie() {
        List<Movie> actualMovies = movieDao.getAll()

        assertTrue(actualMovies.size() == 25)

        assertEqualsList(actualMovies)
    }

    @Test
    void testGetAllMovieWithRequestParameters() {
        RequestParameter priceASC = new RequestParameter("price", SortDirection.ASC)
        RequestParameter priceDESC = new RequestParameter("price", SortDirection.DESC)
        RequestParameter ratingDESC = new RequestParameter("rating", SortDirection.DESC)

        List<Movie> actualMoviesPriceASC = movieDao.getAll(priceASC)
        List<Movie> actualMoviesPriceDESC = movieDao.getAll(priceDESC)
        List<Movie> actualMoviesRatingDESC = movieDao.getAll(ratingDESC)

        assertTrue(actualMoviesPriceASC.size() == 25)
        assertTrue(actualMoviesPriceDESC.size() == 25)
        assertTrue(actualMoviesRatingDESC.size() == 25)

        assertEquals(23, actualMoviesPriceASC.get(0).getId())
        assertEquals(3, actualMoviesPriceDESC.get(0).getId())
        assertEquals(expectedMovies.get(0).getId(), actualMoviesRatingDESC.get(0).getId())
    }

    @Test
    void testGetThreeRandomMovie() {
        List<Movie> actualMovies = movieDao.getThreeRandomMovie()

        assertTrue(actualMovies.size() == 3)
    }

    @Test
    void testGetMovieByIds() {
        List<Integer> moviesIds = new ArrayList<>()
        moviesIds.add(1)
        moviesIds.add(6)

        List<Movie> actualMovies = movieDao.getMoviesByIds(moviesIds)

        assertTrue(actualMovies.size() == 2)

        assertEquals(expectedMovies.get(0).getId(), actualMovies.get(0).getId())
        assertEquals(expectedMovies.get(1).getId(), actualMovies.get(1).getId())
    }

    private void assertEqualsList(List<Movie> actualMovies) {
        Movie expectedFirstMovie = expectedMovies.get(0)
        Movie expectedSecondMovie = expectedMovies.get(1)
        Movie actualFirstMovie = actualMovies.get(0)
        Movie actualSecondMovie = actualMovies.get(5)

        assertEquals(expectedFirstMovie.getId(), actualFirstMovie.getId())
        assertEquals(expectedSecondMovie.getId(), actualSecondMovie.getId())

        assertEquals(expectedFirstMovie.getNameRussian(), actualFirstMovie.getNameRussian())
        assertEquals(expectedSecondMovie.getNameRussian(), actualSecondMovie.getNameRussian())

        assertEquals(expectedFirstMovie.getNameNative(), actualFirstMovie.getNameNative())
        assertEquals(expectedSecondMovie.getNameNative(), actualSecondMovie.getNameNative())

        assertEquals(expectedFirstMovie.getYearOfRelease(), actualFirstMovie.getYearOfRelease())
        assertEquals(expectedSecondMovie.getYearOfRelease(), actualSecondMovie.getYearOfRelease())

        assertEquals(expectedFirstMovie.getDescription(), actualFirstMovie.getDescription())
        assertEquals(expectedSecondMovie.getDescription(), actualSecondMovie.getDescription())

        assertEquals(expectedFirstMovie.getRating(), actualFirstMovie.getRating(), 0.001 as double)
        assertEquals(expectedSecondMovie.getRating(), actualSecondMovie.getRating(), 0.001 as double)

        assertEquals(expectedFirstMovie.getPrice(), actualFirstMovie.getPrice(), 0.001 as double)
        assertEquals(expectedSecondMovie.getPrice(), actualSecondMovie.getPrice(), 0.001 as double)

        assertEquals(expectedFirstMovie.getPicturePath(), actualFirstMovie.getPicturePath())
        assertEquals(expectedSecondMovie.getPicturePath(), actualSecondMovie.getPicturePath())

    }
}
