package edu.ted.web.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.util.UUID;
@AllArgsConstructor
public class UserToken {
    @Getter
    private UUID uuid;
    @Getter
    private String nickname;
    @Getter
    @Setter
    @JsonIgnore
    private transient User user;


    public UserToken(String nickname) {
        this.nickname = nickname;
        uuid = UUID.randomUUID();
    }
}
