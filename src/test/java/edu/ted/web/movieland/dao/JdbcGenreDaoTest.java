package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={MovieLandJavaConfiguration.class})
class JdbcGenreDaoTest {

    @Autowired
    private GenreDao dao;

    @Test
    void getAllGenres() {
        var allGenres = dao.getAllGenres();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }
}