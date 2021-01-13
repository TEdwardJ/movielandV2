package edu.ted.web.movieland.cache;

import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class SpringScheduledCustomGenreCache implements GenreCache {

    private volatile List<Genre> genres;

    private Supplier<List<Genre>> genreListSupplier;

    public SpringScheduledCustomGenreCache(Supplier<List<Genre>> genreListSupplier) {
        this.genreListSupplier = genreListSupplier;
    }

    @Override
    public List<Genre> get() {
        log.debug("Genres cache is to be used");
        checkIfEmpty();
        return genres;
    }

    private void checkIfEmpty() {
        if (genres == null) {
            reset();
        }
    }

    @Override
    @Scheduled(initialDelay = 0, fixedRate=14400000)
    public synchronized void reset() {
        log.info("Cache is to be refreshed");
        genres = genreListSupplier.get();
        log.info("Cache refreshed successfully");
    }
}
