package edu.ted.web.movieland.dao.jdbc.impl;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.jdbc.mapper.GenreRowMapper;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final JdbcTemplate jdbcTemplate;
    private final String getGenresQuery;
    private final String getGenresByMovieQuery;

    private final RowMapper<Genre> genreMapper = new GenreRowMapper();

    public JdbcGenreDao(JdbcTemplate jdbcTemplate, @Value("${getGenresQuery}") String getGenresQuery, @Value("${getGenresByMovieQuery}")String getGenresByMovieQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.getGenresQuery = getGenresQuery;
        this.getGenresByMovieQuery = getGenresByMovieQuery;
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query(getGenresQuery, genreMapper);
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        return  jdbcTemplate.query(getGenresByMovieQuery, genreMapper, id);
    }
}
