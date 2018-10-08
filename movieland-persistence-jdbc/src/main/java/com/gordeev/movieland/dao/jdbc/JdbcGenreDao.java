package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieGenresExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.vo.MovieToGenresVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final String GET_ALL_GENRE_SQL = "SELECT * FROM genre";
    private static final String GET_MOVIES_IDS_BY_GENRE_ID_SQL = "SELECT movie_id FROM movie_genre WHERE genre_id = ?";
    private static final String GET_MOVIES_VO_BY_IDS_SQL = "SELECT mg.movie_id, g.id, g.name FROM movie_genre as mg LEFT JOIN genre as g ON mg.genre_id = g.id WHERE mg.movie_id IN (:moviesIds)";

    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final ResultSetExtractor<List<MovieToGenresVO>> MOVIE_GENRES_EXTRACTOR = new MovieGenresExtractor();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        logger.info("Start processing query to get all genres");
        long startTime = System.currentTimeMillis();

        List<Genre> genres = jdbcTemplate.query(GET_ALL_GENRE_SQL, GENRE_ROW_MAPPER);

        logger.info("Finish processing query to get all genres. It took {} ms", System.currentTimeMillis() - startTime);
        return genres;
    }

    @Override
    public List<MovieToGenresVO> getGenresForMovies(List<Integer> moviesIds) {
        logger.info("Start processing query to get genres for movies with moviesIds: {}", moviesIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moviesIds", moviesIds);

        List<MovieToGenresVO> moviesVO = namedParameterJdbcTemplate.query(GET_MOVIES_VO_BY_IDS_SQL, parameters, MOVIE_GENRES_EXTRACTOR);

        logger.info("Finish processing query to get genres for movies with moviesIds: {}. It took {} ms", moviesIds, System.currentTimeMillis() - startTime);
        return moviesVO;
    }

    @Override
    public List<Integer> getMoviesIdsByGenreId(int genreId) {
        logger.info("Start processing query to get movieIds by genreId");
        long startTime = System.currentTimeMillis();

        List<Integer> movieIds = jdbcTemplate.query(GET_MOVIES_IDS_BY_GENRE_ID_SQL, new SingleColumnRowMapper<>(), genreId);

        logger.info("Finish processing query to get movieIds by genreId. It took {} ms", System.currentTimeMillis() - startTime);
        return movieIds;
    }
}
