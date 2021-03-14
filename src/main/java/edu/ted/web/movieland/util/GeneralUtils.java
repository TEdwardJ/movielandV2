package edu.ted.web.movieland.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class GeneralUtils {

    public static String generateString(int length){
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getEncrypted(String text) {
        return DigestUtils.md5Hex(text);
    }
}
