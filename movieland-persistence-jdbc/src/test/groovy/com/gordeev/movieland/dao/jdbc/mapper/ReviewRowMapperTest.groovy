package com.gordeev.movieland.dao.jdbc.mapper

import com.gordeev.movieland.entity.Review

import java.sql.ResultSet

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class ReviewRowMapperTest extends GroovyTestCase {
    private static final int id = 1
    private static final int movieId = 1
    private static final String text = "Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз."
    private static final int userId = 3

    void testMapRow() {
        ReviewRowMapper reviewRowMapper = new ReviewRowMapper()

        ResultSet resultSet = mock(ResultSet.class)

        when(resultSet.next()).thenReturn(true).thenReturn(false)

        when(resultSet.getInt("id")).thenReturn(id)
        when(resultSet.getInt("movie_id")).thenReturn(movieId)
        when(resultSet.getInt("user_id")).thenReturn(userId)
        when(resultSet.getString("text")).thenReturn(text)

        Review expectedReview = reviewRowMapper.mapRow(resultSet,1)

        assertEquals(id, expectedReview.getId())
        assertEquals(movieId, expectedReview.getMovieId())
        assertEquals(userId, expectedReview.getUser().getId())
        assertEquals(text, expectedReview.getText())
    }
}
