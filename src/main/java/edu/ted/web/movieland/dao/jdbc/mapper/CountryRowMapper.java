package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Country(resultSet.getInt(resultSet.findColumn("CNTR_ID")), resultSet.getString(resultSet.findColumn("CNTR_NAME")));
    }
}
