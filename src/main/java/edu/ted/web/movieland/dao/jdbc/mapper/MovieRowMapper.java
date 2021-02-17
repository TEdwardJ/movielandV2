package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        var movieFromDb = new Movie();
        movieFromDb.setId(resultSet.getInt(resultSet.findColumn("M_ID")));
        movieFromDb.setRussianName(resultSet.getString(resultSet.findColumn("M_RUSSIAN_NAME")));
        movieFromDb.setNativeName(resultSet.getString(resultSet.findColumn("M_NATIVE_NAME")));
        movieFromDb.setDescription(resultSet.getString(resultSet.findColumn("M_DESCRIPTION")));
        movieFromDb.setPrice(resultSet.getDouble(resultSet.findColumn("M_PRICE")));
        movieFromDb.setRating(resultSet.getDouble(resultSet.findColumn("M_RATING")));
        movieFromDb.setPictureUrl(resultSet.getString(resultSet.findColumn("PICTURE_URL")));
        movieFromDb.setReleaseYear(resultSet.getString(resultSet.findColumn("M_RELEASE_YEAR")));
        return movieFromDb;
    }
}
