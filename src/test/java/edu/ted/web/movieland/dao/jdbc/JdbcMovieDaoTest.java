package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.FullSpringNoMvcTest;
import edu.ted.web.movieland.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.common.Sorting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@FullSpringNoMvcTest
class JdbcMovieDaoTest {

    @Autowired
    private MovieDao dao;

    @Test
    void givenAllMoviesRequestedWithSortByPriceAsc_whenSorted_thenCorrect() {
        var allMovies = dao.findAll(new Sorting("price", "asc"));
        assertNotNull(allMovies);
        double price = 0;
        for (var movie : allMovies) {
            assertThat(movie.getPrice(), anyOf(greaterThan(price), closeTo(price, 0.001)));
            price = movie.getPrice();
        }
        assertFalse(allMovies.isEmpty());
    }

    @Test
    void givenAllMoviesRequestedWithSortByRatingDesc_whenSorted_thenCorrect() {
        List<Movie> allMovies = dao.findAll(new Sorting("rating", "desc"));
        assertNotNull(allMovies);
        double rating = allMovies.get(0).getRating();
        for (Movie movie : allMovies) {
            assertThat(movie.getRating(), anyOf(lessThan(rating), closeTo(rating, 0.001)));
            rating = movie.getRating();
        }
        assertFalse(allMovies.isEmpty());
    }

    @Test
    void getNRandomMovies() {
        List<Movie> allMovies = dao.getNRandomMovies(3);
        assertNotNull(allMovies);
        assertFalse(allMovies.isEmpty());
    }

}