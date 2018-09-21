package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.vo.CountryVO;
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

public class MovieCountriesExtractorTest {
    private static final int id = 1;
    private static final int firstCountryId = 2;
    private static final int secondCountryId = 3;
    private static final int thirdCountryId = 4;

    @Test
    public void extractData() throws SQLException {
        ResultSetExtractor<List<CountryVO>> movieGenresExtractor = new MovieCountriesExtractor();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("movie_id")).thenReturn(id);
        when(resultSet.getInt("country_id")).thenReturn(firstCountryId).thenReturn(secondCountryId).thenReturn(thirdCountryId);

        List<Integer> intCountries = new ArrayList<>();
        intCountries.add(firstCountryId);
        intCountries.add(secondCountryId);
        intCountries.add(thirdCountryId);

        List<CountryVO> countryVOList = movieGenresExtractor.extractData(resultSet);

        CountryVO expectedCountryVO = countryVOList.get(0);

        assertTrue(countryVOList.size() == 1);
        assertEquals(id, expectedCountryVO.getMovieId());
        assertEquals(intCountries, expectedCountryVO.getIntCountries());
    }
}