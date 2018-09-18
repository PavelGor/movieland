package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.MovieDao;
import com.gordeev.movieland.dao.jdbc.extractor.MovieExtractor;
import com.gordeev.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.gordeev.movieland.entity.Genre;
import com.gordeev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final ResultSetExtractor<List<Movie>> MOVIE_EXTRACTOR = new MovieExtractor();
    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_MOVIE_SQL = "SELECT m.id, m.name_ru,m.name_eng,m.year_release,c.id as country_id,c.name as country_name,g.id as genre_id,g.name as genre_name,m.description,m.rating,m.price,m.poster FROM movie as m \n" +
            "LEFT JOIN movie_country as mc ON m.id = mc.movie_id \n" +
            "LEFT JOIN country as c ON mc.country_id = c.id\n" +
            "LEFT JOIN movie_genre as mg ON m.id = mg.movie_id\n" +
            "LEFT JOIN genre as g ON mg.genre_id = g.id \n" +
            "ORDER BY m.id,mc.id,mg.id";
    private static final String GET_ALL_GENRE_SQL = "SELECT * FROM genre";

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Movie> getAllMovie() {
        logger.info("Start processing query to get all movies");

        try {
            long startTime = System.currentTimeMillis();

            List<Movie> movies = jdbcTemplate.query(GET_ALL_MOVIE_SQL, MOVIE_EXTRACTOR);

            logger.info("Finish processing query to get all movies. It took {} ms", System.currentTimeMillis() - startTime);

            return movies;
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Not found movies", 1, e);
        }
    }

    @Override
    public List<Genre> getAllGenre() {
        logger.info("Start processing query to get all genres");

        try {
            long startTime = System.currentTimeMillis();

            List<Genre> genres = jdbcTemplate.query(GET_ALL_GENRE_SQL, GENRE_ROW_MAPPER);

            logger.info("Finish processing query to get all genres. It took {} ms", System.currentTimeMillis() - startTime);

            return genres;
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Not found genres", 1, e);
        }
    }
}
