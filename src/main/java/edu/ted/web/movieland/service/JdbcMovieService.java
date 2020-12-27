package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcMovieService {

    public List<Movie> getAllMovies(){
        List<Movie> moviesList = new ArrayList<>();
        return moviesList;
    }
}
