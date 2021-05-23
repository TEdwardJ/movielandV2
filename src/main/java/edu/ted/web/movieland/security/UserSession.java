package edu.ted.web.movieland.security;

import edu.ted.web.movieland.entity.User;
import lombok.Value;

@Value
public class UserSession
{
    String token;
    User user;

}
