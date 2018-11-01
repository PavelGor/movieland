package com.gordeev.movieland.service;

import java.util.Map;
import java.util.Set;

public interface UserService {
    Map<Integer,String> getNickNamesMap(Set<Integer> userIds);
}
