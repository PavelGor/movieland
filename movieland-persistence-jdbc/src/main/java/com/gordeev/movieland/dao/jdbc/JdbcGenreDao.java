package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.GenreDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieGenresExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.vo.MovieToGenresVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final String GET_ALL_GENRE_SQL = "SELECT * FROM genre";
    private static final String GET_MOVIES_IDS_BY_GENRE_ID_SQL = "SELECT movie_id FROM movie_genre WHERE genre_id = :genreId";
    private static final String GET_MOVIES_VO_BY_IDS_SQL = "SELECT mg.movie_id, g.id, g.name FROM movie_genre as mg LEFT JOIN genre as g ON mg.genre_id = g.id WHERE mg.movie_id IN (:moviesIds)";
    private static final String INSERT_GENREID_TO_MOVIE_SQL = "INSERT INTO movie_genre (movie_id, genre_id) VALUES ( :movieId, :genreId)";
    private static final String DELETE_GENRES_FROM_MOVIE_SQL = "DELETE FROM movie_genre WHERE movie_id = :movieId";

    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final ResultSetExtractor<List<MovieToGenresVO>> MOVIE_GENRES_EXTRACTOR = new MovieGenresExtractor();

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcGenreDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        logger.info("Start processing query to get all genres");
        long startTime = System.currentTimeMillis();

        List<Genre> genres = namedParameterJdbcTemplate.query(GET_ALL_GENRE_SQL, GENRE_ROW_MAPPER);

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

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("genreId", genreId);

        List<Integer> movieIds = namedParameterJdbcTemplate.query(GET_MOVIES_IDS_BY_GENRE_ID_SQL, parameters, new SingleColumnRowMapper<>());

        logger.info("Finish processing query to get movieIds by genreId. It took {} ms", System.currentTimeMillis() - startTime);
        return movieIds;
    }

    @Override
    public void addToMovie(Movie movie) {
        int movieId = movie.getId();
        List<Genre> genres = movie.getGenres();
        logger.info("Start processing query to add genres to movie: ", movieId);
        long startTime = System.currentTimeMillis();

        List<MapSqlParameterSource> batchArgs = new ArrayList<>();

        for (Genre genre : genres) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("movieId", movieId);
            parameters.addValue("genreId", genre.getId());
            batchArgs.add(parameters);
        }

        namedParameterJdbcTemplate.batchUpdate(INSERT_GENREID_TO_MOVIE_SQL, batchArgs.toArray(new MapSqlParameterSource[genres.size()]));

        logger.info("Finish processing query to add genres to movie: {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
    }

    @Override
    public void removeFromMovie(Movie movie) {
        logger.info("Start processing query to remove genres from movie {}", movie.getId());
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieId", movie.getId());

        namedParameterJdbcTemplate.update(DELETE_GENRES_FROM_MOVIE_SQL, parameters);

        logger.info("Finish processing query to remove from genres. It took {} ms", System.currentTimeMillis() - startTime);
    }
}
