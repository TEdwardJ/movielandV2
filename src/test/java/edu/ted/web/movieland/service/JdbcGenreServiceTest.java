package edu.ted.web.movieland.service;

import edu.ted.web.movieland.cache.GenreCache;
import edu.ted.web.movieland.configuration.MovieLandJavaConfiguration;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MovieLandJavaConfiguration.class})
class JdbcGenreServiceTest {

    @Mock
    private GenreCache genresCache;

    @Autowired
    @InjectMocks
    private DefaultGenreService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(genresCache.get()).thenReturn(prepareGenreList());
    }

    @Test
    void getAllGenres() {
        var allGenres = service.getAllGenres();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }

    private List<Genre> prepareGenreList() {
        List<Genre> genreList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var genre = new Genre();
            genre.setName("Genre " + i);
            genre.setId(i);
            genreList.add(genre);
        }
        return genreList;
    }
}