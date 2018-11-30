package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.ReviewDao;
import com.gordeev.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.gordeev.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final String GET_REVIEWS_BY_MOVIEID_SQL = "SELECT * FROM review WHERE movie_id = :movieId";
    private static final String ADD_REVIEW_SQL = "INSERT INTO review (movie_id, user_id, text) values (:movieId, :userId, :text);";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Review> REVIEW_ROW_MAPPER = new ReviewRowMapper();

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcReviewDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Review> getByMovieId(int movieId) {
        logger.info("Start processing query to get reviews with moviesId: {}", movieId);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieId", movieId);

        List<Review> movies = namedParameterJdbcTemplate.query(GET_REVIEWS_BY_MOVIEID_SQL, parameters, REVIEW_ROW_MAPPER);

        logger.info("Finish processing query to get reviews with moviesId: {}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public void add(Review review) {
        logger.info("Start inserting query to add review: {}", review);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieId", review.getMovieId());
        parameters.addValue("userId", review.getUser().getId());
        parameters.addValue("text", review.getText());

        namedParameterJdbcTemplate.update(ADD_REVIEW_SQL, parameters);

        logger.info("Finish processing query to add review: {} . It took {} ms", review, System.currentTimeMillis() - startTime);
    }
}
