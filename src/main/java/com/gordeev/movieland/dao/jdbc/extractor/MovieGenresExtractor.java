package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.vo.MovieToGenresVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieGenresExtractor implements ResultSetExtractor<List<MovieToGenresVO>> {
    @Override
    public List<MovieToGenresVO> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        Map<Integer, MovieToGenresVO> MovieToGenresVOMap = new HashMap<>();
        MovieToGenresVO movieToGenresVO;
        List<Genre> genres = new ArrayList<>();

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("movie_id");

            movieToGenresVO = MovieToGenresVOMap.get(currentMovieId);

            if (movieToGenresVO == null){
                movieToGenresVO = new MovieToGenresVO();
                genres = new ArrayList<>();

                movieToGenresVO.setMovieId(currentMovieId);
                movieToGenresVO.setGenres(genres);

                MovieToGenresVOMap.put(currentMovieId, movieToGenresVO);
            }
            Genre genre = new Genre(resultSet.getInt("id"), resultSet.getString("name"));
            genres.add(genre);
        }

        return new ArrayList<>(MovieToGenresVOMap.values());
    }
}
