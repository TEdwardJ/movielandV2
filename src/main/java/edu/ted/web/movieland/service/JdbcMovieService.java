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
        if (Objects.isNull(request.getOrderedColumn())){
            return getAllMovies();
        }
        List<Movie> moviesList = dao
                .getAllMovies()
                .stream()
                .sorted(getMovieComparator(request))
                .collect(Collectors.toList());
        return moviesList;
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

    private Comparator<Movie> getMovieComparator(MovieRequest request) {
        Comparator<Movie> movieComparator = comparators.get(request.getOrderedColumn());
        if (request.getOrderDirection() == OrderDirection.DESC){
            return movieComparator.reversed();
        }
        return movieComparator;
    }

}
