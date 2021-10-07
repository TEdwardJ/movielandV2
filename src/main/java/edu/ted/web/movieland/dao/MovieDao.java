package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.common.Sorting;

import java.util.List;
import java.util.Optional;

public interface MovieDao {

    List<Movie> findAll(Sorting sorting);
    List<Movie> getNRandomMovies(int count);
    List<Movie> getMoviesByGenre(long genreId, Sorting sorting);
    Optional<Movie> getMovieById(long movieId);
    Movie saveOrUpdate(Movie movie);
}
