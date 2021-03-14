package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.MovieRequest;

import java.util.List;

public interface MovieService {

    List<Movie> findAll(MovieRequest request);

    List<Movie> getNRandomMovies(int count);

    List<Movie> getMoviesByGenre(int genreId, MovieRequest request);

    List<Movie> getMovieById(int movieId);


}
