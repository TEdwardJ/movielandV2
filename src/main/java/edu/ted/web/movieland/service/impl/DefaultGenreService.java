package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreDao dao;

    public List<Genre> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        return dao.getGenreByMovieId(id);
    }
}
