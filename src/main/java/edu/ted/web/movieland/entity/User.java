package edu.ted.web.movieland.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {

    private int id;
    private String email;
    @EqualsAndHashCode.Exclude
    private String sole;
    @EqualsAndHashCode.Exclude
    private String password;
    private String nickname;
}
