package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.service.impl.DefaultGenreService;
import edu.ted.web.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(DefaultGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/genres")
    public List<Genre> getAllGenres() {
        return genreService.findAll();
    }
}
