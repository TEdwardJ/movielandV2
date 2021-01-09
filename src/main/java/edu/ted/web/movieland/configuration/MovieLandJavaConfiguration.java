package edu.ted.web.movieland.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.ted.web.movieland.cache.CaffeineGenreCache;
import edu.ted.web.movieland.cache.SpringScheduledCustomGenreCache;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.OrderByColumn;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static edu.ted.web.movieland.web.OrderByColumn.PRICE;
import static edu.ted.web.movieland.web.OrderByColumn.RATING;

@Configuration
@ComponentScan(basePackages = {
        "edu.ted.web.movieland.service",
        "edu.ted.web.movieland.utils",
        "edu.ted.web.movieland.dao"})
@PropertySource("classpath:queries.properties")
@PropertySource("classpath:application.properties")
@EnableScheduling
public class MovieLandJavaConfiguration {

    @Bean
    public PropertyOverrideConfigurer getDataSourceConfigurator() {
        PropertyOverrideConfigurer overrideConfigurator = new PropertyOverrideConfigurer();
        overrideConfigurator.setLocation(new ClassPathResource("dataSource.properties"));
        return overrideConfigurator;
    }

    @Bean("dataSourceConfig")
    public HikariConfig getHikariConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    public DataSource getDataSource(HikariConfig config) {
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Map<OrderByColumn, Comparator<Movie>> getComparators() {
        return new HashMap<OrderByColumn, Comparator<Movie>>() {
            {
                put(PRICE, Comparator.comparing(Movie::getPrice));
                put(RATING, Comparator.comparing(Movie::getRating));
            }
        };
    }

    @Bean
    public LoadingCache<String, List<Genre>> getGenreCache(MovieDao dao){
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
    public SpringScheduledCustomGenreCache getSpringScheduledCustomGenreCache(MovieDao dao){
        return new SpringScheduledCustomGenreCache(()->dao.getAllGenres());
    }
}
