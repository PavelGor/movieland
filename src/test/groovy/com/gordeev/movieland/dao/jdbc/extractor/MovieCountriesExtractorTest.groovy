package com.gordeev.movieland.dao.jdbc.extractor

import com.gordeev.movieland.entity.Country
import com.gordeev.movieland.vo.MovieToCountiesVo
import org.junit.Test
import org.springframework.jdbc.core.ResultSetExtractor

import java.sql.ResultSet
import java.sql.SQLException

import static org.junit.Assert.*
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MovieCountriesExtractorTest {
    private static final int id = 1
    private static final int firstCountryId = 2
    private static final int secondCountryId = 3
    private static final int thirdCountryId = 4
    private static final String firstCountryName = "Италия"
    private static final String secondCountryName = "Испания"
    private static final String thirdCountryName = "США"

    @Test
    void extractData() throws SQLException {
        ResultSetExtractor<List<MovieToCountiesVo>> movieGenresExtractor = new MovieCountriesExtractor()

        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        when(resultSet.getInt("movie_id")).thenReturn(id)
        when(resultSet.getInt("id")).thenReturn(firstCountryId).thenReturn(secondCountryId).thenReturn(thirdCountryId)
        when(resultSet.getString("name")).thenReturn(firstCountryName).thenReturn(secondCountryName).thenReturn(thirdCountryName)

        List<Country> countries = new ArrayList<>()
        Country firstCountry = new Country(firstCountryId, firstCountryName)
        Country secondCountry = new Country(secondCountryId, secondCountryName)
        Country thirdCountry = new Country(thirdCountryId, thirdCountryName)
        countries.add(firstCountry)
        countries.add(secondCountry)
        countries.add(thirdCountry)

        List<MovieToCountiesVo> countryVOList = movieGenresExtractor.extractData(resultSet)

        MovieToCountiesVo expectedCountryVO = countryVOList.get(0)

        assertTrue(countryVOList.size() == 1)
        assertEquals(id, expectedCountryVO.getMovieId())
        assertEquals(countries, expectedCountryVO.getCountries())
    }
}