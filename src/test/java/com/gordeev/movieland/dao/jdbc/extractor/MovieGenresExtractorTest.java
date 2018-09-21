package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.vo.GenreVO;
import org.junit.Test;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieGenresExtractorTest {
    private static final int id = 1;
    private static final int firstGenreId = 2;
    private static final int secondGenreId = 3;
    private static final int thirdGenreId = 4;

    @Test
    public void extractData() throws SQLException {
        ResultSetExtractor<List<GenreVO>> movieGenresExtractor = new MovieGenresExtractor();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("movie_id")).thenReturn(id);
        when(resultSet.getInt("genre_id")).thenReturn(firstGenreId).thenReturn(secondGenreId).thenReturn(thirdGenreId);

        List<Integer> intGenres = new ArrayList<>();
        intGenres.add(firstGenreId);
        intGenres.add(secondGenreId);
        intGenres.add(thirdGenreId);

        List<GenreVO> genreVOList = movieGenresExtractor.extractData(resultSet);

        GenreVO expectedGenreVO = genreVOList.get(0);

        assertTrue(genreVOList.size() == 1);
        assertEquals(id, expectedGenreVO.getMovieId());
        assertEquals(intGenres, expectedGenreVO.getIntGenres());
    }
}