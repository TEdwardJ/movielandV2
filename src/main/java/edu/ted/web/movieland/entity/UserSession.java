package edu.ted.web.movieland.entity;

import lombok.Value;

@Value
public class UserSession
{
    String token;
    User user;

}
