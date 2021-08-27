package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.request.MovieRequest;
import edu.ted.web.movieland.web.dto.ChangeMovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> findAll(MovieRequest request);

    List<Movie> getNRandomMovies(int count);

    List<Movie> getMoviesByGenre(int genreId, MovieRequest request);

    Optional<Movie> getMovieById(GetMovieRequest request);

    Movie saveOrUpdate(ChangeMovieDto movie);
}
