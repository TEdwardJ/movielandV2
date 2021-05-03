package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.web.dto.MovieDto;
import edu.ted.web.movieland.util.MovieMapper;
import edu.ted.web.movieland.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping(path = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    private final MovieMapper mapper;

    @Value("${movies.random.count:3}")
    private int randomMoviesCount;

    @GetMapping(path = "/{movieId}")
    public ResponseEntity<MovieDto> getMoviesById(GetMovieRequest request) {
        log.info("currency in request: {}", request);
        return movieService.getMovieById(request)
                .map(mapper::mapToDTO)
                .map(movieDto -> new ResponseEntity<>(movieDto, OK))
                .orElseGet(() -> new ResponseEntity<>(BAD_REQUEST));
    }

    @GetMapping()
    public List<MovieDto> getAllMovies(MovieRequest request) {
        return mapper.mapToDTOs(movieService.findAll(request));
    }

    @GetMapping(value = "/random")
    public List<MovieDto> getRandomMovies() {
        return mapper.mapToDTOs(movieService.getNRandomMovies(randomMoviesCount));
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<MovieDto> getMoviesByGenre(@PathVariable int genreId, MovieRequest request) {
        return mapper.mapToDTOs(movieService.getMoviesByGenre(genreId, request));
    }


}
