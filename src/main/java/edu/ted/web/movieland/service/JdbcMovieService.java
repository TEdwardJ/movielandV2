package edu.ted.web.movieland.service;

import edu.ted.web.movieland.cache.GenreCache;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JdbcMovieService {
    private MovieDao dao;
    private GenreCache genresCache;

    @Autowired
    public void setGenresCache(GenreCache genresCache) {
        this.genresCache = genresCache;
    }

    @Autowired
    public void setDao(MovieDao dao) {
        this.dao = dao;
    }

    public List<Movie> getAllMovies(MovieRequest request) {
        return dao.getAllMovies(request.getSorting());
    }

    public List<Movie> getNRandomMovies(int number) {
        return dao.getNRandomMovies(number);
    }

    public List<Genre> getAllGenres() {
        return genresCache.get();
    }


    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request) {
        return dao.getMoviesByGenre(genreId, request.getSorting());
    }


}
