package edu.ted.web.movieland.dao.cache;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CustomCachedGenreDao implements GenreDao {

    private final Object monitor = new Object();
    private final GenreDao dao;
    private volatile List<Genre> genres;

    public CustomCachedGenreDao(GenreDao dao) {
        this.dao = dao;
    }

    @Scheduled(fixedRateString = "${genre.cache.lifeInMilliSeconds:14400000}",
               initialDelayString = "${genre.cache.lifeInMilliSeconds:14400000}")
    void refresh() {
        synchronized (monitor) {
            log.info("Cache is to be refreshed");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            genres = Collections.unmodifiableList(dao.findAll());
            log.info("Cache is refreshed successfully, records {}", genres.size());
        }
    }

    @Override
    public List<Genre> findAll() {
        if (genres == null) {
            synchronized (monitor) {
                if (genres == null) {
                    log.info("Scheduled refresh have not been started yet, so Cache is to be refreshed from findAll()");
                    refresh();
                }
            }
        }
        log.debug("Genres cache is to be used");
        return genres;
    }

    @PostConstruct
    void cacheInit(){
        refresh();
    }

}
