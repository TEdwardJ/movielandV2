package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> moviesList = new ArrayList<>();
        return moviesList;
    }

    @Override
    public List<Movie> getNRandomMovies(int number) {
        List<Movie> moviesList = new ArrayList<>();
        return moviesList;
    }
}
