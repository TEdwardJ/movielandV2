package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.request.LoginRequest;
import edu.ted.web.movieland.security.jwt.JwtTokenProvider;
import edu.ted.web.movieland.service.SecurityService;
import edu.ted.web.movieland.web.dto.UserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final static ResponseEntity<Object> BAD_REQUEST_RESPONSE = ResponseEntity.badRequest().build();
    private final static ResponseEntity<Object> OK_RESPONSE = ResponseEntity.ok().build();


    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final UserDetailsService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(LoginRequest loginRequest) {

        var user = (User) userService.loadUserByUsername(loginRequest.getEmail());
        user.setPassword(loginRequest.getPassword());
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword() + user.getSalt()));
            String token = jwtTokenProvider.createToken(user.getEmail()/*, user.getRoles()*/);
            Map<Object, Object> response = new HashMap<>();
            response.put("username", user.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Invalid username or password", e);
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(String uuid) {
        return securityService
                .logout(uuid)
                .map(token -> OK_RESPONSE)
                .orElse(BAD_REQUEST_RESPONSE);
    }
}
