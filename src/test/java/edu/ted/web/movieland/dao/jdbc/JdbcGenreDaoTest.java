package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.GenreDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={NoWebSpringTestConfiguration.class})
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