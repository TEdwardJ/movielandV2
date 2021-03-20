package edu.ted.web.movieland.web.filter;

import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component("reviewSecurityFilter")
@Slf4j
public class ReviewSecurityFilter extends GenericFilterBean {

    private final SecurityService securityService;

    @Autowired
    public ReviewSecurityFilter(SecurityService securityService) {
        System.out.println("ReviewSecurityFilter creation");
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (((HttpServletRequest) servletRequest).getPathInfo().startsWith("/review")) {
            Optional<UserToken> userToken;
            var uuid = Optional.ofNullable(((HttpServletRequest) servletRequest).getHeader("uuid"));
            log.debug("UUID in request {}", uuid);
            if (uuid.isEmpty() || (userToken = securityService.findUserToken(uuid.get())).isEmpty()) {
                ((HttpServletResponse) servletResponse).setStatus(UNAUTHORIZED.value());
                return;
            } else {
                servletRequest.setAttribute("edu.ted.web.movieland.movieLandUserToken", userToken.get());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
