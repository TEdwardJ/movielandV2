package edu.ted.web.movieland.service;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import edu.ted.web.movieland.web.OrderByColumn;
import edu.ted.web.movieland.web.OrderDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class})
class JdbcMovieServiceTest {

    @Autowired
    private JdbcMovieService service;

    @Test
    void getAllMoviesNoSort() {
        List<Movie> allMovies = service.getAllMovies(new MovieRequest());
        assertNotNull(allMovies);
    }

    @Test
    void givenAllMoviesRequestedWithSortByPriceAsc_whenSorted_thenCorrect() {

        List<Movie> allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.PRICE, OrderDirection.ASC));
        double price = 0;
        for (Movie movie : allMovies) {
            assertTrue(movie.getPrice() >= price);
        }
        assertNotNull(allMovies);
    }

    @Test
    void givenAllMoviesRequestedWithSortByRatingDesc_whenSorted_thenCorrect() {
        List<Movie> allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.RATING, OrderDirection.DESC));
        double rating = allMovies.get(0).getRating();
        for (Movie movie : allMovies) {
            assertTrue(movie.getRating() <= rating);
        }
        assertNotNull(allMovies);
    }
}