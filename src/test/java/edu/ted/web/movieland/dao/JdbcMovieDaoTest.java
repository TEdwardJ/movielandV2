package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.MovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.JdbcMovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={MovieLandJavaConfiguration.class})
class JdbcMovieDaoTest {

    @Autowired
    private MovieDao dao;

    @Test
    void getAllMovies() {
        List<Movie> allMovies = dao.getAllMovies();
        assertNotNull(allMovies);
    }

    @Test
    void getNRandomMovies() {
        List<Movie> allMovies = dao.getNRandomMovies(3);
        assertNotNull(allMovies);
    }
}