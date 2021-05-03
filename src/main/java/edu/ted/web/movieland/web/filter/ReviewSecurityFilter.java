package edu.ted.web.movieland.web.filter;

import edu.ted.web.movieland.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


@Component("reviewSecurityFilter")
@Slf4j
public class ReviewSecurityFilter implements Filter {

    private final SecurityService securityService;

    @Autowired
    public ReviewSecurityFilter(SecurityService securityService) {
        System.out.println("ReviewSecurityFilter creation");
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Optional.ofNullable(((HttpServletRequest) servletRequest).getHeader("uuid"))
                .stream()
                .map(securityService::findUserToken)
                .forEach(token -> ((HttpServletRequest) servletRequest).getSession().setAttribute("edu.ted.web.movieland.movieLandUserToken", token.get()));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Autowired
    public void init(SecurityService securityService){
        log.info("Service Object {}", securityService);
    }
}
