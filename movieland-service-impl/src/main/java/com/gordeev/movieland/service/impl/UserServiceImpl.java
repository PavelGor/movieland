package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.UserDao;
import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Map<Integer, String> getNickNamesMap(Set<Integer> userIds) {
        Map<Integer, String> nickNamesMap = new HashMap<>();
        List<User> users = userDao.getUsersByIds(userIds);
        for (User user : users) {
            nickNamesMap.put(user.getId(), user.getNickname());
        }
        return nickNamesMap;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            User user = userDao.getUserByEmail(email);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}
