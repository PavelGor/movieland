package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.vo.CountryVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieCountriesExtractor implements ResultSetExtractor<List<CountryVO>> {
    @Override
    public List<CountryVO> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        Map<Integer, CountryVO> countryVOMap = new HashMap<>();
        CountryVO countryVO;
        List<Integer> intCountries = new ArrayList<>();

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("movie_id");

            countryVO = countryVOMap.get(currentMovieId);

            if (countryVO == null){
                countryVO = new CountryVO();
                intCountries = new ArrayList<>();

                countryVO.setMovieId(currentMovieId);
                countryVO.setIntCountries(intCountries);

                countryVOMap.put(currentMovieId, countryVO);
            }

            intCountries.add(resultSet.getInt("country_id"));
        }

        return new ArrayList<>(countryVOMap.values());
    }
}
