package edu.ted.web.movieland.service;

import edu.ted.web.movieland.MovieLandJavaConfiguration;
import edu.ted.web.movieland.WebMovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
//@WebAppConfiguration("")
@ContextConfiguration(classes={MovieLandJavaConfiguration.class})
class JdbcMovieServiceTest {

    @Autowired
    private JdbcMovieService service;

    @Test
    void getAllMovies() {
        List<Movie> allMovies = service.getAllMovies();
        assertNotNull(allMovies);
    }
}