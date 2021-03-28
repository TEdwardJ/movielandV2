package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CustomCachedGenreDao implements GenreDao {

    private final Object monitor = new Object();
    private final GenreDao dao;
    private volatile List<Genre> genres;

    @PostConstruct
    @Scheduled(fixedRateString = "${genre.cache.lifeInMilliSeconds:14400000}",
               initialDelayString = "${genre.cache.lifeInMilliSeconds:14400000}")
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
        return genres;
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        return dao.getGenreByMovieId(id);
    }

}
