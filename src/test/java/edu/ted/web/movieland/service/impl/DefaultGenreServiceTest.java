package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
class DefaultGenreServiceTest {

    @Mock
    private GenreDao dao;

    //@Autowired
    @InjectMocks
    private DefaultGenreService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(dao.findAll()).thenReturn(prepareGenreList());
    }

    @Test
    void getAllGenres() {
        var allGenres = service.findAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }

    private List<Genre> prepareGenreList() {
        List<Genre> genreList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var genre = new Genre(i, "Genre " + i);
            genreList.add(genre);
        }
        return genreList;
    }
}