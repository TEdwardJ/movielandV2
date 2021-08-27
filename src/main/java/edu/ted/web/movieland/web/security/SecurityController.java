package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.request.LoginRequest;
import edu.ted.web.movieland.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        var userHolder = Optional.ofNullable((User) userService.loadUserByUsername(loginRequest.getEmail()));
        if (!userHolder.isPresent()) {
            throw new BadCredentialsException("Invalid username or password");
        }
        var user = userHolder.get();
        user.setPassword(loginRequest.getPassword());
        try {
            UsernamePasswordAuthenticationToken preparedAuth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword() + user.getSalt());
            var authentication = authenticationManager.authenticate(preparedAuth);
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
            Map<Object, Object> response = Map.of(
                    "username", user.getEmail(),
                    "token", token);
            return new ResponseEntity<>(response, CREATED);
        } catch (AuthenticationException e) {
            log.error("Invalid username or password", e);
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
