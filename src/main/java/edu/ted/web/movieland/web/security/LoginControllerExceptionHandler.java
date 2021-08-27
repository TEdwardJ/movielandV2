package edu.ted.web.movieland.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice(basePackageClasses = {SecurityController.class})
public class LoginControllerExceptionHandler implements AccessDeniedHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity handleConflict(WebRequest webRequest, HttpServletResponse request, Exception exception) {
        return prepareResponse(((ServletWebRequest) webRequest).getRequest().getRequestURI(), exception);
    }

    private ResponseEntity prepareResponse(String path, Exception exception) {
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", FORBIDDEN.value(),
                        "statusMessage", FORBIDDEN.getReasonPhrase(),
                        "message", exception.getMessage(),
                        "path", path);
        var exceptionalResponse = new ResponseEntity(info, FORBIDDEN);
        return exceptionalResponse;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", FORBIDDEN.value(),
                        "statusMessage", FORBIDDEN.getReasonPhrase(),
                        "message", accessDeniedException.getMessage(),
                        "path", request.getRequestURI());

        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //response.sendRedirect("/api/v1/movie/accessDenied");
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", FORBIDDEN.value(),
                        "statusMessage", FORBIDDEN.getReasonPhrase(),
                        "message", authException.getMessage(),
                        "path", request.getRequestURI());

        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }
}
