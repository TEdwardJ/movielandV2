package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.web.annotation.MovieRequestParameter;
import edu.ted.web.movieland.entity.MovieDTO;
import edu.ted.web.movieland.utils.MovieMapper;
import edu.ted.web.movieland.web.MovieRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class MovieController {
    private MovieService movieService;

    private MovieMapper mapper;

    @Autowired
    public MovieController(MovieService movieService, MovieMapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

   @GetMapping(value = "/movie", produces = "application/json")
    public @ResponseBody
    List<MovieDTO> getAllMovies(@MovieRequestParameter MovieRequest request) {
        return mapper.mapToDTOs(movieService.getAllMovies(request));
    }

    @GetMapping(value = "/movie/random", produces = "application/json")
    public @ResponseBody List<MovieDTO> get3RandomMovies() {
        return mapper.mapToDTOs(movieService.getNRandomMovies(3));
    }

    @GetMapping(value = "/movie/genre/{genreId}", produces = "application/json")
    public @ResponseBody
    List<MovieDTO> getMoviesByGenre(@PathVariable int genreId, @MovieRequestParameter MovieRequest request) {
        return mapper.mapToDTOs(movieService.getMoviesByGenre(genreId, request));
    }



}
