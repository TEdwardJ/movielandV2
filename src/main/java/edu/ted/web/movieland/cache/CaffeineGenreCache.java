package edu.ted.web.movieland.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineGenreCache implements GenreCache {

    private final LoadingCache<String, List<Genre>> genresCache;

    @Autowired
    public CaffeineGenreCache(GenreDao dao) {
        LoadingCache<String, List<Genre>> genresCache =
                Caffeine.newBuilder()
                        .expireAfterWrite(4, TimeUnit.HOURS)
                        .maximumSize(1)
                        .build(key -> dao.getAllGenres());
        this.genresCache = genresCache;
    }

    @Override
    public List<Genre> get() {
        log.debug("Genres cache is to be used");
        return genresCache.get("genres");
    }

    @Override
    public void refresh() {
        genresCache.refresh("genres");
        log.debug("Cache refreshed successfully");
    }
}
