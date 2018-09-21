package com.gordeev.movieland.dao.jdbc.extractor;

import com.gordeev.movieland.vo.GenreVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieGenresExtractor implements ResultSetExtractor<List<GenreVO>> {
    @Override
    public List<GenreVO> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        Map<Integer, GenreVO> genreVOMap = new HashMap<>();
        GenreVO genreVO;
        List<Integer> intGenres = new ArrayList<>();

        while (resultSet.next()) {
            int currentMovieId = resultSet.getInt("movie_id");

            genreVO = genreVOMap.get(currentMovieId);

            if (genreVO == null){
                genreVO = new GenreVO();
                intGenres = new ArrayList<>();

                genreVO.setMovieId(currentMovieId);
                genreVO.setIntGenres(intGenres);

                genreVOMap.put(currentMovieId, genreVO);
            }

            intGenres.add(resultSet.getInt("genre_id"));
        }

        return new ArrayList<>(genreVOMap.values());
    }
}
