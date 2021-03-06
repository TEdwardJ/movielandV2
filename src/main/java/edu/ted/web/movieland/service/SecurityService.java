package edu.ted.web.movieland.service;

import edu.ted.web.movieland.security.UserSession;
import edu.ted.web.movieland.request.LoginRequest;

import java.util.Optional;

public interface SecurityService {

    Optional<UserSession> login(LoginRequest loginRequest);

    Optional<UserSession> logout(String uuid);

    Optional<UserSession> findUserToken(String uuid);

}
