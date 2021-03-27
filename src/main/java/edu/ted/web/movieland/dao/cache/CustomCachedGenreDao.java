package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CustomCachedGenreDao implements GenreDao {

    private final Object monitor = new Object();
    private final GenreDao dao;
    private volatile List<Genre> genres;

    @Scheduled(fixedRateString = "${genre.cache.lifeInMilliSeconds:14400000}")
    void refresh() {
        log.info("Cache is to be refreshed");
        var genres = Collections.unmodifiableList(dao.findAll());

        synchronized (monitor) {
            this.genres = genres;
            monitor.notifyAll();
        }
        log.info("Cache is refreshed successfully, records number {}", this.genres.size());
    }

    @Override
    public List<Genre> findAll() {
        var waitingTime = 0L;
        if (genres == null) {
            synchronized (monitor) {
                var time = System.currentTimeMillis();
                while (genres == null && (waitingTime = System.currentTimeMillis() - time) < 10000) {
                    try {
                        var timeoutMillisLeft = 10000 - (System.currentTimeMillis() - time);
                        log.info("Scheduled refresh have not been started yet, so lets wait for {}, left {} ", 10000, timeoutMillisLeft);
                        monitor.wait(timeoutMillisLeft);
                    } catch (InterruptedException e) {
                        log.error("Interruption of cache refresh waiting ", e);
                        return null;
                    }
                }
            }
        }
        log.debug("Genres Cache is to be returned regardless it is set or not, waiting time was {} ms", waitingTime);
        return genres;
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        return dao.getGenreByMovieId(id);
    }

}
