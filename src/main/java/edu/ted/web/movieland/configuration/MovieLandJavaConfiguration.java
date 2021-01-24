package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.cache.CaffeineGenreCache;
import edu.ted.web.movieland.cache.SpringScheduledGenreCache;
import edu.ted.web.movieland.dao.GenreDao;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

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
    @Conditional(CacheTypePropertyCondition.class)
    public CaffeineGenreCache getCaffeineGenreCache(GenreDao dao){
        return new CaffeineGenreCache(dao);
    }

    @Bean
    @Primary
    @Conditional(CacheTypePropertyCondition.class)
    public SpringScheduledGenreCache getSpringScheduledGenreCache(GenreDao dao){
        return new SpringScheduledGenreCache(dao);
    }
}
