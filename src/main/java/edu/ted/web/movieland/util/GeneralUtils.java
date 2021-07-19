package edu.ted.web.movieland.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneralUtils {

    private static PasswordEncoder ENCODER;

    public static String generateStringWithLettersAndNumbers(int length){
        return generateStringWithLettersAndNumbers(length, true, true);
    }

    public static String generateStringWithLettersAndNumbers(int length, boolean useLetters, boolean useNumbers){
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getEncrypted(String text) {
        return ENCODER.encode(text);
    }

    public static void setPasswordEncoder(PasswordEncoder encoder){
        ENCODER = encoder;
    }
}
