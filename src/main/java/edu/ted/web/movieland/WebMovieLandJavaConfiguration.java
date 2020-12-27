package edu.ted.web.movieland;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"edu.ted.web.movieland.controller"})
public class WebMovieLandJavaConfiguration {

}
