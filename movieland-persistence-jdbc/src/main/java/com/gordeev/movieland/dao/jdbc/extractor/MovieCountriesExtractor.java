package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.entity.Country;
import com.gordeev.movieland.vo.MovieToCountiesVo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieCountriesExtractor implements ResultSetExtractor<List<MovieToCountiesVo>> {
    @Override
    public List<MovieToCountiesVo> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        Map<Integer, MovieToCountiesVo> MovieToCountiesVoMap = new HashMap<>();
        MovieToCountiesVo MovieToCountiesVo;
        List<Country> countries = new ArrayList<>();

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("movie_id");

            MovieToCountiesVo = MovieToCountiesVoMap.get(currentMovieId);

            if (MovieToCountiesVo == null) {
                MovieToCountiesVo = new MovieToCountiesVo();
                countries = new ArrayList<>();

                MovieToCountiesVo.setMovieId(currentMovieId);
                MovieToCountiesVo.setCountries(countries);

                MovieToCountiesVoMap.put(currentMovieId, MovieToCountiesVo);
            }
            Country country = new Country(resultSet.getInt("id"), resultSet.getString("name"));
            countries.add(country);
        }

        return new ArrayList<>(MovieToCountiesVoMap.values());
    }
}
