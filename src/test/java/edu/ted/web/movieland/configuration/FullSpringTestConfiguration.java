package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.web.configuration.WebMovieLandJavaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TestDbConfiguration.class, MovieLandJavaConfiguration.class, WebMovieLandJavaConfiguration.class})
@DependsOn("flyway")
public class FullSpringTestConfiguration {
}
