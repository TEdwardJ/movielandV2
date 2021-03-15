package edu.ted.web.movieland.configuration;

import org.springframework.context.annotation.*;

@Configuration
@Import({TestDbConfiguration.class, MovieLandJavaConfiguration.class})
public class NoWebSpringTestConfiguration {

}
