package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MovieExtractor implements ResultSetExtractor<List<Movie>> {
    @Override
    public List<Movie> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Movie> movies = new HashMap<>();
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> genres = new ArrayList<>();
        Set<String> genresSet = new LinkedHashSet<>();
        int lastCountryId = 0;

        Movie movie;

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("id");

            movie = movies.get(currentMovieId);

            if (movie == null) {
                movie = new Movie();

                countries = new ArrayList<>();
                genres = new ArrayList<>();
                genresSet = new LinkedHashSet<>();

                movie.setGenres(genres);
                movie.setCountries(countries);

                movies.put(currentMovieId, movie);

                movie.setId(currentMovieId);
                movie.setNameRussian(resultSet.getString("name_ru"));
                movie.setNameNative(resultSet.getString("name_eng"));
                movie.setYearOfRelease(resultSet.getInt("year_release"));
                movie.setDescription(resultSet.getString("description"));
                movie.setRating(Math.round(resultSet.getDouble("rating") * 100.0) / 100.0);
                movie.setPrice(Math.round(resultSet.getDouble("price") * 100.0) / 100.0);
                movie.setPicturePath(resultSet.getString("poster"));
            }

            int currentCountryId = resultSet.getInt("country_id");
            if (currentCountryId != lastCountryId) {
                countries.add(resultSet.getString("country_name"));
                lastCountryId = currentCountryId;
            }

            genresSet.add(resultSet.getString("genre_name"));
            genres.clear();
            genres.addAll(genresSet);

        }

        return new ArrayList<>(movies.values());
    }
}
