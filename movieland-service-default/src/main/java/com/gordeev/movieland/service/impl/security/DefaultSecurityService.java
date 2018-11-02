package com.gordeev.movieland.service.impl.security;

import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.SecurityService;
import com.gordeev.movieland.service.UserService;
import com.gordeev.movieland.service.impl.security.vo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultSecurityService implements SecurityService{
    @Value("${session.age}")
    private int sessionMaxLifeTime;
    private UserService userService;
    private List<Session> sessionList = new ArrayList<>();

    @Autowired
    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(User user) {
        return userService.getUserByEmail(user.getEmail());
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${session.age}", initialDelayString = "${session.age}")
    private void invalidate(){
        LocalDateTime currentTime = LocalDateTime.now();
        for (Session session : sessionList) {
            LocalDateTime sessionExpireTime = session.getExpireTime();
                if (currentTime.isAfter(sessionExpireTime)) {
                    sessionList.remove(session);
                }
        }
    }

    @Override
    public String createSession(User user) {
        String token = UUID.randomUUID().toString();

        Session session = new Session();
        session.setUser(user);
        session.setUuid(token);

        LocalDateTime time = LocalDateTime.now().plusSeconds(sessionMaxLifeTime);
        session.setExpireTime(time);
        sessionList.add(session);

        return token;
    }

    @Override
    public boolean logoutSession(String uuid) {
        for (Session session : sessionList) {
            if (session.getUuid().equals(uuid)) {
                sessionList.remove(session);
                return true;
            }
        }
        return false;
    }
}
