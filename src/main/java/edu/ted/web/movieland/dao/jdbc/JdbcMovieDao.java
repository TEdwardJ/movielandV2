package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.dao.jdbc.mapper.MovieRowMapper;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.common.Sorting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    private final String getAllMoviesQuery;
    private final String getRandomMoviesQuery;
    private final String getMoviesByGenreQuery;
    private final String movieByIdQuery;

    private final RowMapper<Movie> movieMapper = new MovieRowMapper();

    public JdbcMovieDao(JdbcTemplate jdbcTemplate,
                        @Value("${getAllMoviesQuery}")
                                String getAllMoviesQuery,
                        @Value("${getRandomMoviesQuery}")
                                String getRandomMoviesQuery,
                        @Value("${getMoviesByGenreQuery}")
                                String getMoviesByGenreQuery,
                        @Value("${getMovieByIdQuery}")
                                String movieByIdQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.getAllMoviesQuery = getAllMoviesQuery;
        this.getRandomMoviesQuery = getRandomMoviesQuery;
        this.getMoviesByGenreQuery = getMoviesByGenreQuery;
        this.movieByIdQuery = movieByIdQuery;
    }

    @Override
    public List<Movie> findAll(Sorting sorting) {
        return jdbcTemplate.query(applySorting(getAllMoviesQuery, sorting), movieMapper);
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, Sorting sorting) {
        return jdbcTemplate.query(applySorting(getMoviesByGenreQuery, sorting), movieMapper, genreId);
    }

    @Override
    public List<Movie> getMovieById(int movieId) {
        return jdbcTemplate.query(movieByIdQuery, movieMapper, movieId);
    }

    @Override
    public List<Movie> getNRandomMovies(int count) {
        return jdbcTemplate.query(getRandomMoviesQuery, movieMapper, count);
    }

    private String applySorting(String query, Sorting sorting) {
        if (sorting == null || query == null) {
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
