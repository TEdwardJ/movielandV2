package edu.ted.web.movieland.cache;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
public class SpringScheduledGenreCache implements GenreCache {

    private volatile List<Genre> genres;

    private GenreDao dao;

    public SpringScheduledGenreCache(GenreDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Genre> get() {
        log.debug("Genres cache is to be used");
        checkIfEmpty();
        return genres;
    }

    private void checkIfEmpty() {
        if (genres == null) {
            refresh();
        }
    }

    @Override
    @Scheduled(initialDelay = 0, fixedRate=14400000)
    public synchronized void refresh() {
        log.info("Cache is to be refreshed");
        genres = dao.getAllGenres();
        log.info("Cache refreshed successfully");
    }
}
