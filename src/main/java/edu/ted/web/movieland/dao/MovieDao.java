package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.entity.Sorting;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();
    List<Movie> getAllMovies(Sorting sorting);
    List<Movie> getNRandomMovies(int number);
    List<Movie> getMoviesByGenre(int genreId, Sorting sorting);
}
