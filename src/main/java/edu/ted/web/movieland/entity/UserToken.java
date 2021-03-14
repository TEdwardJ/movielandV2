package edu.ted.web.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

import java.util.UUID;

@Value
public class UserToken {
    private UUID uuid;
    private String nickname;
    @JsonIgnore
    private transient User user;

    public UserToken(String nickname, User user) {
        this.nickname = nickname;
        this.user = user;
        uuid = UUID.randomUUID();
    }

}
