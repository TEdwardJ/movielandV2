package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.User;

public interface UserDao {

    User findUserByEmail(String email);

    boolean isPasswordValid(String email, String encryptedPassword);

    int addUser(User user);
}
