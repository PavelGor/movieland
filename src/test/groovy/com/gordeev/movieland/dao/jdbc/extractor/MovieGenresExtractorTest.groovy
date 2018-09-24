package com.gordeev.movieland.dao.jdbc.extractor

import com.gordeev.movieland.entity.Genre
import com.gordeev.movieland.vo.MovieToGenresVO
import org.junit.Test
import org.springframework.jdbc.core.ResultSetExtractor

import java.sql.ResultSet
import java.sql.SQLException

import static org.junit.Assert.*
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieGenresExtractorTest {
    private static final int id = 1
    private static final int firstGenreId = 2
    private static final int secondGenreId = 3
    private static final int thirdGenreId = 4
    private static final String firstGenreName = "криминал"
    private static final String secondGenreName = "фэнтези"
    private static final String thirdGenreName = "детектив"

    @Test
    void extractData() throws SQLException {
        ResultSetExtractor<List<MovieToGenresVO>> movieGenresExtractor = new MovieGenresExtractor()

        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        when(resultSet.getInt("movie_id")).thenReturn(id)
        when(resultSet.getInt("id")).thenReturn(firstGenreId).thenReturn(secondGenreId).thenReturn(thirdGenreId)
        when(resultSet.getString("name")).thenReturn(firstGenreName).thenReturn(secondGenreName).thenReturn(thirdGenreName)

        List<Genre> genres = new ArrayList<>()
        Genre firstGenre = new Genre(firstGenreId, firstGenreName)
        Genre secondGenre = new Genre(secondGenreId, secondGenreName)
        Genre thirdGenre = new Genre(thirdGenreId, thirdGenreName)
        genres.add(firstGenre)
        genres.add(secondGenre)
        genres.add(thirdGenre)

        List<MovieToGenresVO> movieToGenresVOList = movieGenresExtractor.extractData(resultSet)

        MovieToGenresVO expectedMovieToGenresVO = movieToGenresVOList.get(0)

        assertTrue(movieToGenresVOList.size() == 1)
        assertEquals(id, expectedMovieToGenresVO.getMovieId())
        assertEquals(genres, expectedMovieToGenresVO.getGenres())
    }
}