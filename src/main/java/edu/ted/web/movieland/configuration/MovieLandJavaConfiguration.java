package edu.ted.web.movieland.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration()
@ComponentScan(basePackages = {
        "edu.ted.web.movieland.service",
        "edu.ted.web.movieland.service.impl",
        "edu.ted.web.movieland.util",
        "edu.ted.web.movieland.security",
        "edu.ted.web.movieland.dao"})
@PropertySource("classpath:application.properties")
@EnableScheduling
@EnableCaching
@Import(DBConfiguration.class)
public class MovieLandJavaConfiguration {
    @Autowired
    private CacheManager manager;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("findAllMoviesQueriesCache", "findMoviesByGenreQueriesCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50));
        return cacheManager;
    }

}
