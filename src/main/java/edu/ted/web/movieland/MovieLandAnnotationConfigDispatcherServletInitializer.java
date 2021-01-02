package edu.ted.web.movieland;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
        return new String[] { "/*" };
    }



}
