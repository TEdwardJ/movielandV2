package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
class CaffeineCachedGenreDaoTest {

    @Autowired
    private GenericApplicationContext context;

    private GenreDao cachedDao;

    @BeforeEach
    public void init() {
        cachedDao = (CaffeineCachedGenreDao)context.getBean("testCaffeineCachedGenreDao");
    }

    @Test
    void findAll() {
        var allGenres = cachedDao.findAll();
        assertFalse(allGenres.isEmpty());
        assertThrows(UnsupportedOperationException.class, allGenres::clear);
        assertThrows(UnsupportedOperationException.class, () -> allGenres.set(0, new Genre(1111,"1111")));
        var genre = allGenres.get(0);
        assertNotNull(genre.getName());
        assertTrue(genre.getId() > 0);
        var allGenresOneMore = cachedDao.findAll();
        assertSame(allGenres, allGenresOneMore);
    }


}