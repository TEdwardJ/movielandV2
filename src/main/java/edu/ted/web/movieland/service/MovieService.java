package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;

import java.util.List;

public interface MovieService {

    public List<Movie> getAllMovies(MovieRequest request);

    public List<Movie> getNRandomMovies(int count);

    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request);
}
