package edu.ted.web.movieland.common;

import org.springframework.http.ResponseEntity;

public class SecurityConstants {

    private final static ResponseEntity<Object> BAD_REQUEST_RESPONSE = ResponseEntity.badRequest().build();

    private final static ResponseEntity<Object> OK_RESPONSE = ResponseEntity.ok().build();

    public final static String TOKEN_PREFIX = "Bearer_";

    public final static String AUTHORIZATION_HEADER_NAME = "Authorization";
}
