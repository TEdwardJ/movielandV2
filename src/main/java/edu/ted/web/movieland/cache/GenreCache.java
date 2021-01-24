package edu.ted.web.movieland.cache;

import edu.ted.web.movieland.entity.Genre;

import java.util.List;

public interface GenreCache {
    List<Genre> get();
    void refresh();
}
