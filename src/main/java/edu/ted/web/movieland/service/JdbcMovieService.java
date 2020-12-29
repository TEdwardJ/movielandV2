package edu.ted.web.movieland.service;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcMovieService {
    @Autowired
    private MovieDao dao;

    public List<Movie> getAllMovies() {
        List<Movie> moviesList = dao.getAllMovies();
        return moviesList;
    }

    public List<Movie> getNRandomMovies(int number) {
        return dao.getNRandomMovies(number);
    }
}
