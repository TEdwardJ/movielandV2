package edu.ted.web.movieland.controller;

import edu.ted.web.movieland.entity.MovieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class MovieController {

    @GetMapping(value = "/movie", produces = "application/json")
    public ResponseEntity<List<MovieDTO>> getAllMovies(){
        List<MovieDTO> moviesList = new ArrayList<>();
        return ResponseEntity.of(Optional.ofNullable(moviesList));
    }
}
