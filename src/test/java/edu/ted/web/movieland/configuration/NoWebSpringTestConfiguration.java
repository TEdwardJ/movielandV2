package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.cache.CaffeineCachedGenreDao;
import edu.ted.web.movieland.dao.cache.CustomCachedGenreDao;
import org.springframework.context.annotation.*;


@Configuration
@Import({TestDbConfiguration.class, MovieLandJavaConfiguration.class})
@DependsOn("flyway")
public class NoWebSpringTestConfiguration {

    @Bean(value = "testCustomCachedGenreDao")
    @Lazy
    public static GenreDao customGenreDao(GenreDao jdbcGenreDao, GenreDao cachedDao) {
        if (cachedDao instanceof CustomCachedGenreDao) {
            return cachedDao;
        }
        return new CustomCachedGenreDao(jdbcGenreDao);
    }

    @Bean("testCaffeineCachedGenreDao")
    @Lazy
    public static GenreDao caffeineGenreDao(GenreDao jdbcGenreDao, GenreDao cachedDao) {
        if (cachedDao instanceof CaffeineCachedGenreDao) {
            return cachedDao;
        }
        return new CaffeineCachedGenreDao(jdbcGenreDao);
    }
}
