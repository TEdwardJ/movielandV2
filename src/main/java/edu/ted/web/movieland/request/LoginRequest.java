package edu.ted.web.movieland.request;

import lombok.Value;

@Value
public class LoginRequest {
    String email;
    String password;
}
