package com.gordeev.movieland.service.impl;

import com.gordeev.movieland.dao.UserDao;
import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DefaultUserService implements UserService{
    private UserDao userDao;

    @Autowired
    public DefaultUserService(UserDao userDao) {
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
}
