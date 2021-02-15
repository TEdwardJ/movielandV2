package edu.ted.web.movieland.dao.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineCachedGenreDao implements GenreDao {

    private final LoadingCache<String, List<Genre>> genresCache;
    private GenreDao dao;

    @Autowired
    public CaffeineCachedGenreDao(GenreDao dao) {
        this.dao = dao;
        LoadingCache<String, List<Genre>> genresCache =
                Caffeine.newBuilder()
                        .expireAfterWrite(4, TimeUnit.HOURS)
                        .maximumSize(1)
                        .build(key -> dao.findAll());
        this.genresCache = genresCache;
    }

    @Override
    public List<Genre> findAll() {
        log.debug("Genres cache is to be used");
        return genresCache.get("genres");
    }
}
