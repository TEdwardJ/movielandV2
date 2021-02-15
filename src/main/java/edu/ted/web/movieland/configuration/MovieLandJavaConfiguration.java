package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.cache.CaffeineCachedGenreDao;
import edu.ted.web.movieland.dao.cache.SpringScheduledCachedGenreDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration()
@ComponentScan(basePackages = {
        "edu.ted.web.movieland.service",
        "edu.ted.web.movieland.utils",
        "edu.ted.web.movieland.dao"})
@PropertySource("classpath:application.properties")
@EnableScheduling
@Import(DBConfiguration.class)
public class MovieLandJavaConfiguration {

    @Bean
    public GenreDao genreDao(GenreDao dao, @Value("${cacheType:SpringScheduledCachedGenreDao}") String cacheType){
        if(cacheType.endsWith("CaffeineCachedGenreDao")) {
            return new CaffeineCachedGenreDao(dao);
        }
        return new SpringScheduledCachedGenreDao(dao);
    }
}
