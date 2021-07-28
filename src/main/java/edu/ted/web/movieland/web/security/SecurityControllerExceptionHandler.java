package edu.ted.web.movieland.web.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice(basePackageClasses = {SecurityController.class})
class LoginControllerExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleConflict(WebRequest webRequest, HttpServletResponse request, Exception exception) {
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", FORBIDDEN.value(),
                        "statusMessage", FORBIDDEN.getReasonPhrase(),
                        "message", exception.getMessage(),
                        "path", ((ServletWebRequest) webRequest).getRequest().getRequestURI());
        var exceptionalResponse = new ResponseEntity(info, FORBIDDEN);
        return exceptionalResponse;
    }
}
