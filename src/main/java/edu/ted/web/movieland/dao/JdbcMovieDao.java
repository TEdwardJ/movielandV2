package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
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


    private void map(ResultSet rs, List<Movie> moviesList) {
        try {
            Movie movieFromDb = new Movie();
            movieFromDb.setId(rs.getInt(rs.findColumn("M_ID")));
            movieFromDb.setTitle(rs.getString(rs.findColumn("M_TITLE")));
            movieFromDb.setTitleEng(rs.getString(rs.findColumn("M_TITLE_EN")));
            movieFromDb.setDescription(rs.getString(rs.findColumn("M_DESCRIPTION")));
            movieFromDb.setPrice(rs.getDouble(rs.findColumn("M_PRICE")));
            movieFromDb.setRating(rs.getDouble(rs.findColumn("M_RATING")));
            movieFromDb.setPictureUrl(rs.getString(rs.findColumn("PICTURE_URL")));
            movieFromDb.setReleaseYear(rs.getString(rs.findColumn("M_RELEASE_YEAR")));
            moviesList.add(movieFromDb);
        } catch (SQLException throwables) {
            //TODO: when logging is connected, then put exception into log
            //throwables.printStackTrace();
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> moviesList = new ArrayList<>();
        jdbc.query(allMoviesSelect, (rs) -> {
            map(rs, moviesList);
            if (!rs.next()) {
                return;
            }
        });
        return moviesList;
    }

    @Override
    public List<Movie> getNRandomMovies(int number) {
        List<Movie> moviesList = new ArrayList<>();
        jdbc.query(randomMoviesSelect, (rs) -> {
            while (number > moviesList.size()) {
                map(rs, moviesList);
                if (!rs.next()) {
                    return;
                }
            }
        });
        return moviesList;
    }
}
