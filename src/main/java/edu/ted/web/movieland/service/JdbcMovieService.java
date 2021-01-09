package edu.ted.web.movieland.service;

import edu.ted.web.movieland.cache.GenreCache;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.MovieRequest;
import edu.ted.web.movieland.web.OrderByColumn;
import edu.ted.web.movieland.web.OrderDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JdbcMovieService {
    private MovieDao dao;
    private Map<OrderByColumn, Comparator<Movie>> comparators;
    private GenreCache genresCache;

    @Autowired
    public void setGenresCache(GenreCache genresCache) {
        this.genresCache = genresCache;
    }

    @Autowired
    public void setDao(MovieDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setComparators(Map<OrderByColumn, Comparator<Movie>> comparators) {
        this.comparators = comparators;
    }

    public List<Movie> getAllMovies(MovieRequest request) {
        return getListSorted(dao.getAllMovies().stream(), getMovieComparator(request));
    }

    public List<Movie> getNRandomMovies(int number) {
        return dao.getNRandomMovies(number);
    }

    public List<Genre> getAllGenres() {
        return genresCache.get();
    }


    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request) {
        return getListSorted(dao.getMoviesByGenre(genreId).stream(), getMovieComparator(request));
    }


    private Comparator<Movie> getMovieComparator(MovieRequest request) {
        Comparator<Movie> movieComparator = comparators.get(request.getOrderedColumn());
        if (request.getOrderDirection() == OrderDirection.DESC){
            return movieComparator.reversed();
        }
        return Optional.ofNullable(movieComparator).orElse(Comparator.comparing(o->0));
    }

    private List<Movie> getListSorted(Stream<Movie> movieStream, Comparator<Movie> comparator){
        return movieStream
                .sorted(comparator)
                .collect(Collectors.toList());
    }


}
