package edu.ted.web.movieland.controller;

import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.service.JdbcGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class GenreController {

    private JdbcGenreService genreService;

    @Autowired
    public GenreController(JdbcGenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/movie/genre", produces = "application/json")
    public @ResponseBody
    List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}
