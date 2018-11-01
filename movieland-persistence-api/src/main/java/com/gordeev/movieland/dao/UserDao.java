package com.gordeev.movieland.dao;

import com.gordeev.movieland.entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> getUsersByIds(Set<Integer> userIds);
}
