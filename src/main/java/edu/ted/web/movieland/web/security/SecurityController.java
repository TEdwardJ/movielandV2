package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.request.LoginRequest;
import edu.ted.web.movieland.service.SecurityService;
import edu.ted.web.movieland.web.dto.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final static ResponseEntity<Object> BAD_REQUEST_RESPONSE = ResponseEntity.badRequest().build();
    private final static ResponseEntity<Object> OK_RESPONSE = ResponseEntity.ok().build();

    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(LoginRequest loginRequest) {
        return securityService
                .login(loginRequest)
                .map(session -> new UserToken(session.getToken(), session.getUser().getNickname()))
                .map(token -> new ResponseEntity<>(token, CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(String uuid) {
        return securityService
                .logout(uuid)
                .map(token -> OK_RESPONSE)
                .orElse(BAD_REQUEST_RESPONSE);
    }
}
