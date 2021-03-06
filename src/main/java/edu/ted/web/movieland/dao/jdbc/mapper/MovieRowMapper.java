package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        var movieFromDb = new Movie();
        movieFromDb.setId(resultSet.getInt("M_ID"));
        movieFromDb.setRussianName(resultSet.getString("M_RUSSIAN_NAME"));
        movieFromDb.setNativeName(resultSet.getString("M_NATIVE_NAME"));
        movieFromDb.setDescription(resultSet.getString("M_DESCRIPTION"));
        movieFromDb.setPrice(resultSet.getDouble("M_PRICE"));
        movieFromDb.setRating(resultSet.getDouble("M_RATING"));
        movieFromDb.setPictureUrl(resultSet.getString("PICTURE_URL"));
        movieFromDb.setReleaseYear(resultSet.getString("M_RELEASE_YEAR"));
        return movieFromDb;
    }
}
