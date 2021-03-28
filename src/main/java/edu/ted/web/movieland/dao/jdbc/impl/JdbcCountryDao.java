package edu.ted.web.movieland.dao.jdbc.impl;

import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.dao.jdbc.mapper.CountryRowMapper;
import edu.ted.web.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {

    private final JdbcTemplate jdbcTemplate;
    private final String getCountriesByMovieIdQuery;
    private CountryRowMapper countryRowMapper = new CountryRowMapper();

    public JdbcCountryDao(JdbcTemplate jdbcTemplate, @Value("${getCountriesByMovieIdQuery}")String getCountriesByMovieIdQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.getCountriesByMovieIdQuery = getCountriesByMovieIdQuery;
    }

    @Override
    public List<Country> getCountriesByMovieId(long movieId) {
        return jdbcTemplate.query(getCountriesByMovieIdQuery, countryRowMapper, Long.valueOf(movieId));
    }
}
