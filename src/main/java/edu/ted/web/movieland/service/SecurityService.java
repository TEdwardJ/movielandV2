package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.entity.UserToken;

import java.util.Optional;

public interface SecurityService {

    Optional<UserToken> login(String email, String password);

    UserToken logout(String uuid);

    Optional<UserToken> findUserToken(String uuid);

    int addUser(User user);

}
