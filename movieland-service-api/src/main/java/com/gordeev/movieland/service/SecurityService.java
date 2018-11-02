package com.gordeev.movieland.service;

import com.gordeev.movieland.entity.User;

import java.util.Optional;

public interface SecurityService {
    Optional<User> authenticate(User user);

    String createSession(User user);

    boolean logoutSession(String uuid);

    User getUser(String uuid);
}
