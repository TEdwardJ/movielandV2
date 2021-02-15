package edu.ted.web.movieland.service;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService{

    private final GenreDao dao;

    public List<Genre> getAllGenres() {
        return dao.getAllGenres();
    }
}
