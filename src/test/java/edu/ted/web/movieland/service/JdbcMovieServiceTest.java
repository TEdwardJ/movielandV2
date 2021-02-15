package edu.ted.web.movieland.service;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.request.MovieRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class})
class JdbcMovieServiceTest {

    @Autowired
    private MovieService service;

    @Test
    void getAllMoviesNoSort() {
        var allMovies = service.findAll(new MovieRequest());
        assertNotNull(allMovies);
    }

    @Test
    void givenAllMoviesRequestedWithSruortByPriceAsc_whenSorted_thenCorrect() {
        var allMovies = service.findAll(new MovieRequest("price", "asc"));
        assertNotNull(allMovies);
        double price = 0;
        for (var movie : allMovies) {
            assertTrue(movie.getPrice() >= price);
            assertThat(movie.getPrice(), anyOf(greaterThan(price), closeTo(price, 0.001)));
            price = movie.getPrice();
        }
        assertFalse(allMovies.isEmpty());
    }

    @Test
    void givenAllMoviesRequestedWithSortByRatingDesc_whenSorted_thenCorrect() {
        var allMovies = service.findAll(new MovieRequest("rating", "desc"));
        assertNotNull(allMovies);
        double rating = allMovies.get(0).getRating();
        for (var movie : allMovies) {
            assertThat(movie.getRating(), anyOf(lessThan(rating), closeTo(rating, 0.001)));
            rating = movie.getRating();
        }
        assertFalse(allMovies.isEmpty());
    }

}