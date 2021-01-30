package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={MovieLandJavaConfiguration.class})
class JdbcGenreDaoTest {

    @Autowired
    @Qualifier("jdbcGenreDao")
    private GenreDao dao;

    @Test
    void getAllGenres() {
        var allGenres = dao.getAllGenres();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }
}