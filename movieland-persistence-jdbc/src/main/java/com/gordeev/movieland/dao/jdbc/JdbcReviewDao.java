package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.ReviewDao;
import com.gordeev.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.gordeev.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final String GET_REVIEWS_BY_MOVIEID_SQL = "SELECT * FROM review WHERE movie_id = ?";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Review> REVIEW_ROW_MAPPER = new ReviewRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> getByMovieId(int movieId) {
        logger.info("Start processing query to get reviews with moviesId: {}", movieId);
        long startTime = System.currentTimeMillis();

        List<Review> movies = jdbcTemplate.query(GET_REVIEWS_BY_MOVIEID_SQL, REVIEW_ROW_MAPPER, movieId);

        logger.info("Finish processing query to get reviews with moviesId: {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return movies;
    }
}