package com.gordeev.movieland.service.impl.security.vo;

import com.gordeev.movieland.entity.User;

import java.time.LocalDateTime;

public class Session {
    private User user;
    private LocalDateTime expireTime;
    private String uuid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
