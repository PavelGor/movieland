package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.Review;
import com.gordeev.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();

        review.setId(resultSet.getInt("id"));
        review.setMovieId(resultSet.getInt("movie_id"));
        review.setText(resultSet.getString("text"));

        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        review.setUser(user);

        return review;
    }
}
