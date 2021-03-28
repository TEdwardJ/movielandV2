package edu.ted.web.movieland.dao.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class CaffeineCachedGenreDao implements GenreDao {
    @Value("${genre.cache.lifeInMilliSeconds:14400000}")
    private long fixedRateValue;
    private LoadingCache<String, List<Genre>> genresCache;
    private final GenreDao dao;



    @PostConstruct
    public void init(){
        this.genresCache = Caffeine.newBuilder()
                .expireAfterWrite(fixedRateValue, TimeUnit.MILLISECONDS)
                .maximumSize(1)
                .build(key -> Collections.unmodifiableList(dao.findAll()));
    }

    @Override
    public List<Genre> findAll() {
        log.debug("Genres cache is to be used");
        return genresCache.get("genres");
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        return dao.getGenreByMovieId(id);
    }
}
