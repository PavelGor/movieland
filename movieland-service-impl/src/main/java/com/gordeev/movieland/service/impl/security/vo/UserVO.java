package com.gordeev.movieland.service.impl.security.vo;

public class UserVO {
    private String uuid;
    private String nickname;

    public UserVO(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }
}
