package edu.ted.web.movieland.controller;

import edu.ted.web.movieland.annotation.MovieRequestParameter;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.entity.MovieDTO;
import edu.ted.web.movieland.service.JdbcMovieService;
import edu.ted.web.movieland.utils.MovieMapper;
import edu.ted.web.movieland.web.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class MovieController {
    @Autowired
    private JdbcMovieService service;

    @Autowired
    private MovieMapper mapper;

    @GetMapping(value = "/movie", produces = "application/json")
    public @ResponseBody
    List<MovieDTO> getAllMovies(@MovieRequestParameter MovieRequest request) {
        List<Movie> allMovies = service.getAllMovies(request);

        List<MovieDTO> moviesList = mapper.movieListToMovieDTOList(allMovies);
        return moviesList;
    }

    @GetMapping(value = "/movie/random", produces = "application/json")
    public ResponseEntity<List<MovieDTO>> get3RandomMovies() {
        List<Movie> allMovies = service.getNRandomMovies(3);
        List<MovieDTO> moviesList = mapper.movieListToMovieDTOList(allMovies);
        return ResponseEntity.of(Optional.ofNullable(moviesList));
    }

    @GetMapping(value = "/movie/genre", produces = "application/json")
    public @ResponseBody
    List<Genre> getAllGenres() {
        return service.getAllGenres();
    }

}
