package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.web.configuration.WebMovieLandJavaConfiguration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MovieLandAnnotationConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MovieLandJavaConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMovieLandJavaConfiguration.class};
    }


    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/v1/*" };
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy("reviewSecurityFilter");
        filterProxy.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        servletContext.addFilter("reviewSecurityFilter", filterProxy)
                .addMappingForUrlPatterns(null, true, "/*");
    }


}
