package edu.ted.web.movieland.web.interceptor;

import edu.ted.web.movieland.security.SessionHandler;
import edu.ted.web.movieland.web.annotation.UserRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        SessionHandler.setUserSession(null);
    }

    private void setResponseStatus(HttpServletResponse response, Boolean result) {
        if (!result) {
            response.setStatus(UNAUTHORIZED.value());
        }
    }

    private boolean checkSecurity(HttpServletRequest request) {
        return !Objects.isNull(SessionHandler.getUserSession());
    }
}
