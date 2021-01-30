package edu.ted.web.movieland.service;

import edu.ted.web.movieland.cache.GenreCache;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService{

    private GenreCache genresCache;

    @Autowired
    public DefaultGenreService(GenreCache genresCache) {
        this.genresCache = genresCache;
    }

    public List<Genre> getAllGenres() {
        return genresCache.get();
    }
}