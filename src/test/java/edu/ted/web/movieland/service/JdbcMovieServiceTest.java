package edu.ted.web.movieland.service;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import edu.ted.web.movieland.web.OrderByColumn;
import edu.ted.web.movieland.web.OrderDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class})
class JdbcMovieServiceTest {

    @Mock
    private MovieDao movieDao;

    @Autowired
    @InjectMocks
    private JdbcMovieService service;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        when(movieDao.getAllMovies()).thenReturn(prepareMovieList());
    }

    @Test
    void getAllMoviesNoSort() {
        when(movieDao.getAllMovies()).thenReturn(prepareMovieList());
        List<Movie> allMovies = service.getAllMovies(new MovieRequest());
        assertNotNull(allMovies);
    }

    @Test
    void givenAllMoviesRequestedWithSortByPriceAsc_whenSorted_thenCorrect() {
        List<Movie> allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.PRICE, OrderDirection.ASC));
        assertNotNull(allMovies);
        double price = 0;
        for (Movie movie : allMovies) {
            assertTrue(movie.getPrice() >= price);
        }
        assertFalse(allMovies.isEmpty());
    }

    @Test
    void givenAllMoviesRequestedWithSortByRatingDesc_whenSorted_thenCorrect() {
        List<Movie> allMovies = service.getAllMovies(new MovieRequest(OrderByColumn.RATING, OrderDirection.DESC));
        assertNotNull(allMovies);
        double rating = allMovies.get(0).getRating();
        for (Movie movie : allMovies) {
            assertTrue(movie.getRating() <= rating);
        }
        assertFalse(allMovies.isEmpty());
    }

    private List<Movie> prepareMovieList() {
        List<Movie> list = new ArrayList<>();
        Random ratingGenerator = new Random();
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setTitle("Movie " + i);
            movie.setRating(ratingGenerator.nextDouble() * 10);
            movie.setPrice(ratingGenerator.nextDouble() * 100);
            list.add(movie);
        }
        return list;
    }
}