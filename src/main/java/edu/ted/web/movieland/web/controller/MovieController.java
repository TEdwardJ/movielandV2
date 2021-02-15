package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.web.entity.MovieDTO;
import edu.ted.web.movieland.util.MovieMapper;
import edu.ted.web.movieland.web.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {
    private MovieService movieService;

    private MovieMapper mapper;

    @Autowired
    public MovieController(MovieService movieService, MovieMapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

   @GetMapping
    public List<MovieDTO> getAllMovies(MovieRequest request) {
        return mapper.mapToDTOs(movieService.findAll(request));
    }

    @GetMapping(value = "/random")
    public List<MovieDTO> get3RandomMovies() {
        return mapper.mapToDTOs(movieService.getNRandomMovies(3));
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<MovieDTO> getMoviesByGenre(@PathVariable int genreId, MovieRequest request) {
        return mapper.mapToDTOs(movieService.getMoviesByGenre(genreId, request));
    }
}
