package edu.ted.web.movieland.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", FORBIDDEN.value(),
                        "statusMessage", FORBIDDEN.getReasonPhrase(),
                        "message", authException.getMessage(),
                        "path", request.getRequestURI());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));
    }

}
