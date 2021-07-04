package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.CountryDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;


@Configuration
@Import({TestDbConfiguration.class, MovieLandJavaConfiguration.class})
@DependsOn("flyway")
public class NoWebSpringTestConfiguration {

    @Bean("testMockCountryDao")
    @Profile("parallelEnrichmentTest")
    @Primary
    public CountryDao caffeineCountryDao(CountryDao countryDao) {
        return Mockito.spy(countryDao);
    }

}
