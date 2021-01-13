package edu.ted.web.movieland.cache;

import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CaffeineGenreCache implements GenreCache {
    private LoadingCache<String, List<Genre>> genresCache;

    @Autowired
    public void setGenresCache(LoadingCache<String, List<Genre>> genresCache) {
        this.genresCache = genresCache;
    }

    @Override
    public List<Genre> get() {
        log.debug("Genres cache is to be used");
        return genresCache.get("genres");
    }

    @Override
    public void reset() {
        genresCache.refresh("genres");
        log.debug("Cache refreshed successfully");
    }
}
