package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.web.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final MovieDao dao;

    public List<Movie> findAll(MovieRequest request) {
        return dao.findAll(request.getSorting());
    }

    public List<Movie> getNRandomMovies(int count) {
        return dao.getNRandomMovies(count);
    }

    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request) {
        return dao.getMoviesByGenre(genreId, request.getSorting());
    }


}
