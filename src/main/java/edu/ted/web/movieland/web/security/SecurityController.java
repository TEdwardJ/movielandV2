package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestParam String email, @RequestParam String password) {
        var userToken = userService.authorize(email, password);
        HttpStatus status;
        if (userToken != null) {
            status = HttpStatus.CREATED;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(userToken, status);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(String uuid) {
        HttpStatus status;
        if (userService.logout(uuid) != null){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        };
        return new ResponseEntity<>(null, status);
    }
}
