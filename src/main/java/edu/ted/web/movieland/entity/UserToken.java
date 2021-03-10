package edu.ted.web.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
public class UserToken {
    @Getter
    private UUID uuid;
    @Getter
    private String nickname;


    public UserToken(String nickname) {
        this.nickname = nickname;
        uuid = UUID.randomUUID();
    }
}
