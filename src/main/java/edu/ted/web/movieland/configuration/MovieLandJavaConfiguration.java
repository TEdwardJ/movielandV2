package edu.ted.web.movieland.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.cache.CaffeineGenreCache;
import edu.ted.web.movieland.cache.SpringScheduledCustomGenreCache;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration()
@ComponentScan(basePackages = {
        "edu.ted.web.movieland.service",
        "edu.ted.web.movieland.utils",
        "edu.ted.web.movieland.dao"})
@PropertySource("classpath:queries.properties")
@PropertySource("classpath:application.properties")
@EnableScheduling
@Import(DBConfiguration.class)
public class MovieLandJavaConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LoadingCache<String, List<Genre>> getGenreCache(GenreDao dao){
        return Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.HOURS)
                .maximumSize(1)
                .build(key -> dao.getAllGenres());
    }

    @Bean
    @Conditional(CacheTypePropertyCondition.class)
    public CaffeineGenreCache getCaffeineGenreCache(){
        return new CaffeineGenreCache();
    }

    @Bean
    @Primary
    @Conditional(CacheTypePropertyCondition.class)
    public SpringScheduledCustomGenreCache getSpringScheduledCustomGenreCache(GenreDao dao){
        return new SpringScheduledCustomGenreCache(()->dao.getAllGenres());
    }
}
