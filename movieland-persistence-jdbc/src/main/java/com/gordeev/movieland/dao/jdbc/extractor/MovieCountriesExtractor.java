package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.vo.MovieToCountiesVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieCountriesExtractor implements ResultSetExtractor<List<MovieToCountiesVO>> {
    @Override
    public List<MovieToCountiesVO> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        Map<Integer, MovieToCountiesVO> MovieToCountiesVoMap = new HashMap<>();
        MovieToCountiesVO MovieToCountiesVO;
        List<Country> countries = new ArrayList<>();

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("movie_id");

            MovieToCountiesVO = MovieToCountiesVoMap.get(currentMovieId);

            if (MovieToCountiesVO == null) {
                MovieToCountiesVO = new MovieToCountiesVO();
                countries = new ArrayList<>();

                MovieToCountiesVO.setMovieId(currentMovieId);
                MovieToCountiesVO.setCountries(countries);

                MovieToCountiesVoMap.put(currentMovieId, MovieToCountiesVO);
            }
            Country country = new Country(resultSet.getInt("id"), resultSet.getString("name"));
            countries.add(country);
        }

        return new ArrayList<>(MovieToCountiesVoMap.values());
    }
}
