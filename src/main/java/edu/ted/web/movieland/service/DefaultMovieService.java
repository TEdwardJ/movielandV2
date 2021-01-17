package edu.ted.web.movieland.service;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class DefaultMovieService implements MovieService {
    private MovieDao dao;

    @Autowired
    public DefaultMovieService(MovieDao dao) {
        this.dao = dao;
    }

    public List<Movie> getAllMovies(MovieRequest request) {
        return dao.getAllMovies(request.getSorting());
    }

    public List<Movie> getNRandomMovies(int count) {
        return dao.getNRandomMovies(count);
    }

    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request) {
        return dao.getMoviesByGenre(genreId, request.getSorting());
    }


}
