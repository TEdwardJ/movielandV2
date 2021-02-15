package edu.ted.web.movieland.service;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService{

    private GenreDao dao;

    @Autowired
    public DefaultGenreService(GenreDao dao) {
        this.dao = dao;
    }

    public List<Genre> getAllGenres() {
        return dao.getAllGenres();
    }
}
