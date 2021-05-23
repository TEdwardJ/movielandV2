package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.cache.CaffeineCachedGenreDao;
import edu.ted.web.movieland.dao.cache.CustomCachedGenreDao;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;


@Configuration
@Import({TestDbConfiguration.class, MovieLandJavaConfiguration.class})
@DependsOn("flyway")
public class NoWebSpringTestConfiguration {

    @Bean(value = "testCaffeineCachedGenreDao")
    @Profile("testCaffeineCachedGenreDao")
    public CaffeineCachedGenreDao caffeineGenreDao(@Qualifier("jdbcGenreDao")GenreDao jdbcGenreDao, @Qualifier("genreDao")GenreDao cachedDao) {
        if (cachedDao instanceof CaffeineCachedGenreDao) {
            return (CaffeineCachedGenreDao)cachedDao;
        }
        return new CaffeineCachedGenreDao(jdbcGenreDao);
    }

    @Bean(value = "testCustomCachedGenreDao")
    @Profile("testCustomCachedGenreDao")
    public CustomCachedGenreDao customGenreDao(GenreDao jdbcGenreDao, @Qualifier("genreDao")GenreDao cachedDao) {
        if (cachedDao instanceof CustomCachedGenreDao) {
            return (CustomCachedGenreDao)cachedDao;
        }
        return new CustomCachedGenreDao(jdbcGenreDao);
    }

    @Bean("testMockCountryDao")
    @Profile("parallelEnrichmentTest")
    @Primary
    public CountryDao caffeineCountryDao(CountryDao countryDao) {
        return Mockito.spy(countryDao);
    }

}
