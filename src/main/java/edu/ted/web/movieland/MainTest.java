package edu.ted.web.movieland;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MainTest {

    public static void main(String[] args) {
        Long x = 12L;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        var password = "coldbeer";
        var salt = "qpvjnzlwgq";
        var encodedPassword = encoder.encode(password + salt);
        var encodedPassword2 = encoder.encode(password + salt);
        System.out.println(encodedPassword);
        System.out.println(encodedPassword2);
        System.out.println(encoder.matches(password+salt, encodedPassword));
        System.out.println(encoder.matches(password+salt, encodedPassword2));
    }
}
