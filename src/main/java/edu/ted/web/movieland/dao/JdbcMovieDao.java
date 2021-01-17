package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.entity.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbc;
    @Value("${allMoviesSelect}")
    private String allMoviesSelect;

    @Value("${randomMoviesSelect}")
    private String randomMoviesSelect;

    @Value("${moviesByGenreSelect}")
    private String moviesByGenreSelect;

    @Autowired
    private RowMapper<Movie> movieMapper;

    @Override
    public List<Movie> getAllMovies() {
        return jdbc.query(allMoviesSelect, movieMapper);
    }

    @Override
    public List<Movie> getAllMovies(Sorting sorting) {
        return jdbc.query(applySorting(allMoviesSelect, sorting), movieMapper);
    }

    private String applySorting(String query, Sorting sorting) {
        if (sorting == null || query == null){
            return query;
        }
        return new StringBuilder()
                .append("SELECT subQuery.* ")
                .append("  FROM (")
                .append(query)
                .append(") as subQuery ")
                .append("ORDER BY ")
                .append(sorting.getColumn().getDbColumnName())
                .append(" ")
                .append(sorting.getDirection())
                .toString();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, Sorting sorting) {
        return jdbc.query(applySorting(moviesByGenreSelect, sorting), movieMapper, genreId);
    }

    @Override
    public List<Movie> getNRandomMovies(int number) {
        return jdbc.query(randomMoviesSelect, movieMapper, number);
    }
}
