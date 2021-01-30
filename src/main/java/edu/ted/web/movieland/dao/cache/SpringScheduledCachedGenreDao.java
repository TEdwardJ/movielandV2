package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
public class SpringScheduledCachedGenreDao implements GenreDao {

    private GenreDao dao;
    private volatile List<Genre> genres;

    public SpringScheduledCachedGenreDao(GenreDao dao) {
        this.dao = dao;
    }

    private void checkIfEmpty() {
        if (genres == null) {
            refresh();
        }
    }

    @Scheduled(initialDelay = 0, fixedRate=14400000)
    private void refresh() {
        log.info("Cache is to be refreshed");
        genres = dao.getAllGenres();
        log.info("Cache refreshed successfully");
    }

    @Override
    public List<Genre> getAllGenres() {
        log.debug("Genres cache is to be used");
        checkIfEmpty();
        return genres;
    }
}
