package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Autowired
    private JdbcTemplate jdbc;
    @Value("${allMoviesSelect}")
    private String allMoviesSelect;

    @Value("${randomMoviesSelect}")
    private String randomMoviesSelect;

    @Value("${allGenresSelect}")
    private String allGenresSelect;

    @Value("${moviesByGenreSelect}")
    private String moviesByGenreSelect;

    @Autowired
    private RowMapper<Movie> movieMapper;

    @Autowired
    private RowMapper<Genre> genreMapper;


    private void mapMovie(ResultSet rs, List<Movie> moviesList) {
        try {
            int number = moviesList.size();
            Movie movieFromDb = movieMapper.mapRow(rs, number + 1);
            moviesList.add(movieFromDb);
        } catch (SQLException throwables) {
            //TODO: when logging is connected, then put exception into log
            //throwables.printStackTrace();
        }
    }

    private void mapGenre(ResultSet rs, List<Movie> moviesList) {
        try {
            int number = moviesList.size();
            Movie movieFromDb = movieMapper.mapRow(rs, number + 1);
            moviesList.add(movieFromDb);
        } catch (SQLException throwables) {
            //TODO: when logging is connected, then put exception into log
            //throwables.printStackTrace();
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> moviesList = jdbc.query(allMoviesSelect, movieMapper);
        return moviesList;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genresList = jdbc.query(allGenresSelect, genreMapper);
        return genresList;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> moviesList = jdbc.query(moviesByGenreSelect,movieMapper,genreId);
        return moviesList;
    }

    @Override
    public List<Movie> getNRandomMovies(int number) {
        List<Movie> moviesList = new ArrayList<>();
        jdbc.query(randomMoviesSelect, (rs) -> {
            while (number > moviesList.size()) {
                mapMovie(rs, moviesList);
                if (!rs.next()) {
                    return;
                }
            }
        });
        return moviesList;
    }
}
