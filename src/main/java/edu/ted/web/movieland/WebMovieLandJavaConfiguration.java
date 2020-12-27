package edu.ted.web.movieland;

import edu.ted.web.movieland.utils.MovieMapper;
import edu.ted.web.movieland.utils.MovieMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"edu.ted.web.movieland.controller"})
public class WebMovieLandJavaConfiguration {
    @Bean
    public MovieMapper getMovieMapper() {
        return new MovieMapperImpl();
    }
}
