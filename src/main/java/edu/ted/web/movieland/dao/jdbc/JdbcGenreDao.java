package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.jdbc.mapper.GenreRowMapper;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final JdbcTemplate jdbcTemplate;
    private final String getGenresSql;

    private final RowMapper<Genre> genreMapper = new GenreRowMapper();

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate, @Value("${getGenresQuery}")String getGenresSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.getGenresSql = getGenresSql;
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query(getGenresSql, genreMapper);
    }
}
