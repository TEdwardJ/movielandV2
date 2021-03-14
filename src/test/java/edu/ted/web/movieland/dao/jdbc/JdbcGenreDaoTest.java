package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.GenreDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FullSpringNoMvcTest
class JdbcGenreDaoTest {

    @Autowired
    @Qualifier("jdbcGenreDao")
    private GenreDao dao;

    @Test
    void getAllGenres() {
        var allGenres = dao.findAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }
}