package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;

import java.util.Arrays;
import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();
    List<Movie> getNRandomMovies(int number);

    List<Genre> getAllGenres();

    List<Movie> getMoviesByGenre(int genreId);
}
