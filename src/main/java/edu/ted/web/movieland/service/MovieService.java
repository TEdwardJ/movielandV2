package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.MovieRequest;

import java.util.List;

public interface MovieService {

    public List<Movie> findAll(MovieRequest request);

    public List<Movie> getNRandomMovies(int count);

    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request);
}
