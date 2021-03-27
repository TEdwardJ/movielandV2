package edu.ted.web.movieland.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class UserSession
{
    private UUID token;
    private User user;

}
