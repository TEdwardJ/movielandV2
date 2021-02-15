package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.entity.Sorting;

import java.util.List;

public interface MovieDao {

    List<Movie> findAll(Sorting sorting);
    List<Movie> getNRandomMovies(int count);
    List<Movie> getMoviesByGenre(int genreId, Sorting sorting);
}
