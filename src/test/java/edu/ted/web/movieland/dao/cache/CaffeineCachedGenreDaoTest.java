package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
class CaffeineCachedGenreDaoTest {

    @Autowired
    private CaffeineCachedGenreDao cachedDao;

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