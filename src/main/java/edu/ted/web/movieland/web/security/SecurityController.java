package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.*;

@RestController
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(String email, String password) {
        return securityService
                .login(email, password)
                .map(token -> new ResponseEntity<>(token, CREATED))
                .orElseGet(() -> new ResponseEntity<>(BAD_REQUEST));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(String uuid) {
        HttpStatus status;
        if (securityService.logout(uuid) != null) {
            status = OK;
        } else {
            status = BAD_REQUEST;
        }
        return new ResponseEntity<>(null, status);
    }
}
