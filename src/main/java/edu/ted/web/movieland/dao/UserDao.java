package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findUserByEmail(String email);

    boolean isPasswordValid(String email, String encryptedPassword);

    int addUser(User user);
}
