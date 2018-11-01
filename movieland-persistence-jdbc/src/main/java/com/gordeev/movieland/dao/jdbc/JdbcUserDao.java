package com.gordeev.movieland.dao.jdbc;

import com.gordeev.movieland.dao.UserDao;
import com.gordeev.movieland.dao.jdbc.mapper.UserRowMapper;
import com.gordeev.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class JdbcUserDao implements UserDao {
    private static final String GET_USERS_BY_IDS_SQL = "SELECT * FROM USERS WHERE id IN (:userIds)";

    private static final RowMapper<User> USER_ROW_MAPPER = new UserRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getUsersByIds(Set<Integer> userIds) {
        logger.info("Start processing query to get users with userIds: {}", userIds);
        long startTime = System.currentTimeMillis();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userIds", userIds);

        List<User> users = namedParameterJdbcTemplate.query(GET_USERS_BY_IDS_SQL, parameters, USER_ROW_MAPPER);

        logger.info("Finish processing query to get users with userIds: {}. It took {} ms", userIds, System.currentTimeMillis() - startTime);
        return users;
    }
}
