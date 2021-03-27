package edu.ted.web.movieland.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class UserSession
{
    private String token;
    private User user;

}
