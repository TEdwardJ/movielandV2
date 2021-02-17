package edu.ted.web.movieland;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.web.configuration.WebMovieLandJavaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MovieLandJavaConfiguration.class, WebMovieLandJavaConfiguration.class, FlywayTestConfiguration.class})
public class FullSpringTestConfiguration {
}
