package edu.ted.web.movieland.utils;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcMovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movieFromDb = new Movie();
        movieFromDb.setId(resultSet.getInt(resultSet.findColumn("M_ID")));
        movieFromDb.setTitle(resultSet.getString(resultSet.findColumn("M_TITLE")));
        movieFromDb.setTitleEng(resultSet.getString(resultSet.findColumn("M_TITLE_EN")));
        movieFromDb.setDescription(resultSet.getString(resultSet.findColumn("M_DESCRIPTION")));
        movieFromDb.setPrice(resultSet.getDouble(resultSet.findColumn("M_PRICE")));
        movieFromDb.setRating(resultSet.getDouble(resultSet.findColumn("M_RATING")));
        movieFromDb.setPictureUrl(resultSet.getString(resultSet.findColumn("PICTURE_URL")));
        movieFromDb.setReleaseYear(resultSet.getString(resultSet.findColumn("M_RELEASE_YEAR")));
        return movieFromDb;
    }
}
