package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbc;
    @Value("${allMoviesSelect}")
    private String allMoviesSelect;

    @Value("${randomMoviesSelect}")
    private String randomMoviesSelect;

    @Value("${moviesByGenreSelect}")
    private String moviesByGenreSelect;

    @Autowired
    private RowMapper<Movie> movieMapper;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> moviesList = jdbc.query(allMoviesSelect, movieMapper);
        return moviesList;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> moviesList = jdbc.query(moviesByGenreSelect, movieMapper, genreId);
        return moviesList;
    }

    @Override
    public List<Movie> getNRandomMovies(int number) {
        List<Movie> moviesList = jdbc.query(randomMoviesSelect, movieMapper, number);
        return moviesList;
    }
}
