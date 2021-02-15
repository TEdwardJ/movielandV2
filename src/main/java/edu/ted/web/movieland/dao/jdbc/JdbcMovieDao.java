package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.dao.jdbc.mapper.MovieRowMapper;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.entity.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${allMoviesSelect}")
    private String allMoviesSelect;

    @Value("${randomMoviesSelect}")
    private String randomMoviesSelect;

    @Value("${moviesByGenreSelect}")
    private String moviesByGenreSelect;

    private RowMapper<Movie> movieMapper = new MovieRowMapper();

    @Override
    public List<Movie> getAllMovies(Sorting sorting) {
        return jdbcTemplate.query(applySorting(allMoviesSelect, sorting), movieMapper);
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, Sorting sorting) {
        return jdbcTemplate.query(applySorting(moviesByGenreSelect, sorting), movieMapper, genreId);
    }

    @Override
    public List<Movie> getNRandomMovies(int count) {
        return jdbcTemplate.query(randomMoviesSelect, movieMapper, count);
    }

    private String applySorting(String query, Sorting sorting) {
        if (sorting == null || query == null){
            return query;
        }
        return new StringBuilder()
                .append("SELECT subQuery.* ")
                .append(" FROM (")
                .append(query)
                .append(") as subQuery ")
                .append("ORDER BY ")
                .append(sorting.getOrderedColumn().getDbColumnName())
                .append(" ")
                .append(sorting.getOrderDirection())
                .toString();
    }
}
