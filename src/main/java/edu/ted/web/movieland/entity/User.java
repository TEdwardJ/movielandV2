package edu.ted.web.movieland.entity;

import lombok.Data;

@Data
public class User {

    private int id;
    private String email;
    private String sole;
    private String password;
    private String nickname;
}
