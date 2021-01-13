package edu.ted.web.movieland.utils;

import edu.ted.web.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {

        Genre genreFromDb = new Genre();
        genreFromDb.setId(resultSet.getInt(resultSet.findColumn("GNR_ID")));
        genreFromDb.setName(resultSet.getString(resultSet.findColumn("GNR_NAME")));
        return genreFromDb;
    }
}
