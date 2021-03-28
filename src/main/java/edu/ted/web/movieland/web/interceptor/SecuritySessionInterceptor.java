package edu.ted.web.movieland.web.interceptor;

import edu.ted.web.movieland.web.annotation.UserRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Component
@Slf4j
public class SecuritySessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("Request handler class name: {}", handler.getClass().getName());
        return Optional.of(handler)
                .filter(objectHandler -> objectHandler instanceof HandlerMethod)
                .map(objectHandler -> ((HandlerMethod) objectHandler))
                .filter(handlerMethod -> handlerMethod.hasMethodAnnotation(UserRequired.class))
                .map(handlerMethod -> checkSecurity(request))
                .stream()
                .peek(result -> setResponseStatus(response, result))
                .findFirst()
                .orElse(true);
    }

    private void setResponseStatus(HttpServletResponse response, Boolean result) {
        if (!result) {
            response.setStatus(UNAUTHORIZED.value());
        }
    }

    private boolean checkSecurity(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession().getAttribute("edu.ted.web.movieland.movieLandUserToken")).isPresent();
    }
}
