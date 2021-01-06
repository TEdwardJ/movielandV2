package edu.ted.web.movieland.service;

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
    @Autowired
    private MovieDao dao;

    private Map<OrderByColumn, Comparator<Movie>> comparators = new HashMap<OrderByColumn, Comparator<Movie>>(){
        {
            put(OrderByColumn.PRICE, Comparator.comparing(Movie::getPrice));
            put(OrderByColumn.RATING, Comparator.comparing(Movie::getRating));
        }
    };

    public List<Movie> getAllMovies(MovieRequest request) {
        return getListSorted(dao.getAllMovies().stream(), getMovieComparator(request));
    }

    List<Movie> getAllMovies() {
        List<Movie> moviesList = dao.getAllMovies();
        return moviesList;
    }

    public List<Movie> getNRandomMovies(int number) {
        return dao.getNRandomMovies(number);
    }

    public List<Genre> getAllGenres() {
        return dao.getAllGenres();
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
