package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private JdbcTemplate jdbc;
    private String allGenresSelect;

    @Autowired
    private RowMapper<Genre> genreMapper;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbc, RowMapper<Genre> mapper, @Value("${allGenresSelect}")String allGenresSelect) {
        this.jdbc = jdbc;
        this.genreMapper = mapper;
        this.allGenresSelect = allGenresSelect;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genresList = jdbc.query(allGenresSelect, genreMapper);
        return genresList;
    }
}
