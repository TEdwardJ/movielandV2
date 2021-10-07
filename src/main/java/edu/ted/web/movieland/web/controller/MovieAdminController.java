package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.web.dto.ChangeMovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/movie", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieAdminController {

    private final MovieService movieService;

    @PostMapping()
    @PreAuthorize("hasRole='ADMIN'")
    public ResponseEntity<ChangeMovieDto> changeMovieDB(@RequestBody ChangeMovieDto movie){
        return ResponseEntity.ok().body(movieService.saveOrUpdate(movie));
    }

}