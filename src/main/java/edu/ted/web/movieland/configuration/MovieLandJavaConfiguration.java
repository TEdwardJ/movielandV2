package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.cache.CaffeineCachedGenreDao;
import edu.ted.web.movieland.dao.cache.CustomCachedGenreDao;
import org.springframework.beans.factory.annotation.Value;
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
@Import(DBConfiguration.class)
public class MovieLandJavaConfiguration {

    @Bean
    @Primary
    public GenreDao genreDao(GenreDao jdbcGenreDao,
                             @Value("${genre.cache.type:CustomCachedGenreDao}") String cacheType){
        if(cacheType.endsWith("CaffeineCachedGenreDao")) {
            return new CaffeineCachedGenreDao(jdbcGenreDao);
        }
        return new CustomCachedGenreDao(jdbcGenreDao);
    }
}
