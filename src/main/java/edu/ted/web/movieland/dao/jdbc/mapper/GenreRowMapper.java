package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(resultSet.getInt(resultSet.findColumn("GNR_ID")), resultSet.getString(resultSet.findColumn("GNR_NAME")));
    }
}
