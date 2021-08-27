package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.common.Sorting;
import edu.ted.web.movieland.request.MovieRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@FullSpringNoMvcTest
class JdbcMovieDaoTest {

    @Autowired
    private MovieDao dao;

    @PersistenceContext
    private EntityManager manager;

    @Test
    @Transactional
    void testPersistence(){
        var movieFromDB0 = dao.getMovieById(110);
        System.out.println("!movieFromDB0:"+movieFromDB0);
        Movie movie  = new Movie();
        movie.setId(110);
        movie = manager.find(Movie.class, movie.getId());
        System.out.println("!movie:"+movie);
        var movieFromDB = dao.getMovieById(110);
        System.out.println("!movieFromDB:"+movieFromDB);

    }

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

    @Test
    void getMoviesByGenreTest() {
        List<Movie> allMovies = dao.getMoviesByGenre(63, new MovieRequest().getSorting());
        assertNotNull(allMovies);
        assertFalse(allMovies.isEmpty());
    }

}