package edu.ted.web.movieland.service;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import edu.ted.web.movieland.web.entity.OrderByColumn;
import edu.ted.web.movieland.web.entity.OrderDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
        var allMovies = service.getAllMovies(new MovieRequest());
        assertNotNull(allMovies);
    }

    @Test
    void givenAllMoviesRequestedWithSruortByPriceAsc_whenSorted_thenCorrect() {
        var allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.PRICE, OrderDirection.ASC));
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
        var allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.RATING, OrderDirection.DESC));
        assertNotNull(allMovies);
        double rating = allMovies.get(0).getRating();
        for (var movie : allMovies) {
            assertThat(movie.getRating(), anyOf(lessThan(rating), closeTo(rating, 0.001)));
            rating = movie.getRating();
        }
        assertFalse(allMovies.isEmpty());
    }

}