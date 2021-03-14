package edu.ted.web.movieland;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MovieLandJavaConfiguration.class, TestDbConfiguration.class})
public class NoWebSpringTestConfiguration {
}
