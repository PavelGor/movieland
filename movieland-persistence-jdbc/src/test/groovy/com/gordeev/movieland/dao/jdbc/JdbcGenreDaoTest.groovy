package com.gordeev.movieland.dao.jdbc

import com.gordeev.movieland.dao.GenreDao
import com.gordeev.movieland.entity.Genre
import com.gordeev.movieland.entity.Movie
import com.gordeev.movieland.vo.MovieToGenresVO
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
class JdbcGenreDaoTest extends GroovyTestCase {
    List<MovieToGenresVO> expectedMovieToGenresVO
    List<Integer> expectedMoviesIds

    @Autowired
    GenreDao genreDao

    @Before
    void setup() {
        expectedMoviesIds = new ArrayList<>()
        expectedMoviesIds.add(2)
        expectedMoviesIds.add(6)
        expectedMoviesIds.add(19)

        expectedMovieToGenresVO = new ArrayList<>()
        Genre firstGenre = new Genre()
        firstGenre.setId(1)
        firstGenre.setName("драма")

        Genre secondGenre = new Genre()
        secondGenre.setId(6)
        secondGenre.setName("биография")

        List<Genre> genres = new ArrayList<>()
        genres.add(firstGenre)
        genres.add(secondGenre)

        MovieToGenresVO movieToGenresVO = new MovieToGenresVO()
        movieToGenresVO.setGenres(genres)
        movieToGenresVO.setMovieId(4)
        expectedMovieToGenresVO.add(movieToGenresVO)
    }

    @Test
    void testGetAll() {
        Genre firstGenre = new Genre()
        firstGenre.setId(1)
        firstGenre.setName("драма")
        Genre secondGenre = new Genre()
        secondGenre.setId(2)
        secondGenre.setName("криминал")

        List<Genre> genres = genreDao.getAll()

        assertTrue(!genres.isEmpty())
        assertEquals(firstGenre.getId(),genres.get(0).getId())
        assertEquals(secondGenre.getId(),genres.get(1).getId())

        assertEquals(firstGenre.getName(),genres.get(0).getName())
        assertEquals(secondGenre.getName(),genres.get(1).getName())
    }

    @Test
    void testGetGenresForMovies() {
        List<Integer> moviesList = new ArrayList<Integer>()
        moviesList.add(4)
        List<MovieToGenresVO> actualMovieToGenresVO = genreDao.getGenresForMovies(moviesList)

        assertEquals(expectedMovieToGenresVO.size(),actualMovieToGenresVO.size())
        assertEquals(expectedMovieToGenresVO.get(0).getGenres(),actualMovieToGenresVO.get(0).getGenres())
    }

    @Test
    void testGetMoviesIdsByGenreId() {
        int genreId = 4
        List<Integer> actualMoviesIds = genreDao.getMoviesIdsByGenreId(genreId)
        int count = 0
        for (int i = 0; i < expectedMoviesIds.size(); i++) {
            for (int j = 0; j < actualMoviesIds.size(); j++) {
                if (expectedMoviesIds[i] == actualMoviesIds[j]){
                    count++
                }
            }
        }

        assertEquals(expectedMoviesIds.size(), count)

    }
}
