package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Map<Integer,String> getNickNamesMap(Set<Integer> userIds);

    Optional<User> getUserByEmail(String email);
}
