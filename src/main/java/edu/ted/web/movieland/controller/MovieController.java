package edu.ted.web.movieland.controller;

import edu.ted.web.movieland.annotation.MovieRequestParameter;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.MovieDTO;
import edu.ted.web.movieland.service.JdbcMovieService;
import edu.ted.web.movieland.utils.MovieMapper;
import edu.ted.web.movieland.web.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return mapper.movieListToMovieDTOList(service.getAllMovies(request));
    }

    @GetMapping(value = "/movie/random", produces = "application/json")
    public @ResponseBody List<MovieDTO> get3RandomMovies() {
        return mapper.movieListToMovieDTOList(service.getNRandomMovies(3));
    }

    @GetMapping(value = "/movie/genre/{genreId}", produces = "application/json")
    public @ResponseBody
    List<MovieDTO> getMoviesByGenre(@PathVariable int genreId, @MovieRequestParameter MovieRequest request) {
        return mapper.movieListToMovieDTOList(service.getMoviesByGenre(genreId, request));
    }

    @GetMapping(value = "/movie/genre", produces = "application/json")
    public @ResponseBody
    List<Genre> getAllGenres() {
        return service.getAllGenres();
    }



}
