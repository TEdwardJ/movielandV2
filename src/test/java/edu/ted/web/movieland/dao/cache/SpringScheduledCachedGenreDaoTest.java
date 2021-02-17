package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.jdbc.JdbcGenreDao;
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
class SpringScheduledCachedGenreDaoTest {

    @Autowired
    private GenericApplicationContext context;
    @Autowired
    private JdbcGenreDao jdbcGenreDao;
    private SpringScheduledCachedGenreDao cachedDao;

    @BeforeEach
    public void init() {
        context.registerBean(SpringScheduledCachedGenreDao.class, jdbcGenreDao);
        cachedDao = (SpringScheduledCachedGenreDao)context.getBean("edu.ted.web.movieland.dao.cache.SpringScheduledCachedGenreDao");
    }

    @Test
    void findAll() {
        var allGenres = cachedDao.findAll();
        assertFalse(allGenres.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> allGenres.clear());
        assertThrows(UnsupportedOperationException.class, () -> allGenres.set(0, new Genre(1111,"1111")));
        var genre = allGenres.get(0);
        assertNotNull(genre.getName());
        assertNotNull(genre.getId());
        var allGenresOneMore = cachedDao.findAll();
        assertSame(allGenres, allGenresOneMore);
    }
}