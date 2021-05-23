package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@FullSpringNoMvcTest
@ActiveProfiles({"testCustomCachedGenreDao"})
class CustomCachedGenreDaoTest {

    @Autowired
    @Qualifier("testCustomCachedGenreDao")
    private GenreDao cachedDao;

    @Test
    void findAll() {
        var allGenres = cachedDao.findAll();
        assertFalse(allGenres.isEmpty());
        assertThrows(UnsupportedOperationException.class, allGenres::clear);
        assertThrows(UnsupportedOperationException.class, () -> allGenres.set(0, new Genre(1111,"1111")));
        var genre = allGenres.get(0);
        assertNotNull(genre.getName());
        assertNotNull(genre.getId());
        var allGenresOneMore = cachedDao.findAll();
        assertSame(allGenres, allGenresOneMore);
    }
}