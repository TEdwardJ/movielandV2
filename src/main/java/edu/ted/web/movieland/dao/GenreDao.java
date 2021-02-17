package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> findAll();
}
