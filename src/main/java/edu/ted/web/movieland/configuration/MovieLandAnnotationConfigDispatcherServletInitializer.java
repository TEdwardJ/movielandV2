package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.web.configuration.WebMovieLandJavaConfiguration;
import edu.ted.web.movieland.web.filter.ReviewSecurityFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

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
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
        filterProxy.setTargetBeanName("reviewSecurityFilter");
        return new Filter[]{filterProxy};
    }


}
