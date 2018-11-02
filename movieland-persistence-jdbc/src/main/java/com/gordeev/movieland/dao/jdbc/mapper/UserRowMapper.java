package com.gordeev.movieland.dao.jdbc.mapper;

import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.vo.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setNickname(resultSet.getString("nickname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        String roleString = resultSet.getString("role_name");
        UserRole userRole = UserRole.getByName(roleString.trim());
        user.setUserRole(userRole);

        return user;
    }
}
