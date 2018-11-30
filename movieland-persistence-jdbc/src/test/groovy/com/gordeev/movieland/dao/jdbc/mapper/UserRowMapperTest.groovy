package com.gordeev.movieland.dao.jdbc.mapper

import com.gordeev.movieland.entity.User
import com.gordeev.movieland.entity.UserRole

import java.sql.ResultSet

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class UserRowMapperTest extends GroovyTestCase {
    private static final int id = 1
    private static final String nickname = "Ivan"
    private static final String email = "ivan@mail.com"
    private static final String password = "neivan@mail.com"
    private static final String roleName = "USER"

    void testMapRow() {
        UserRowMapper userRowMapper = new UserRowMapper()

        ResultSet resultSet = mock(ResultSet.class)

        when(resultSet.next()).thenReturn(true).thenReturn(false)

        when(resultSet.getInt("id")).thenReturn(id)
        when(resultSet.getString("nickname")).thenReturn(nickname)
        when(resultSet.getString("email")).thenReturn(email)
        when(resultSet.getString("password")).thenReturn(password)
        when(resultSet.getString("role_name")).thenReturn(roleName)

        User expectedUser = userRowMapper.mapRow(resultSet,1)

        assertEquals(id, expectedUser.getId())
        assertEquals(nickname, expectedUser.getNickname())
        assertEquals(email, expectedUser.getEmail())
        assertEquals(password, expectedUser.getPassword())
        assertEquals(UserRole.USER, expectedUser.getUserRole())
    }
}
