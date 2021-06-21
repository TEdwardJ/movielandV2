package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.dao.jdbc.mapper.CountryRowMapper;
import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.EnrichmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
@ActiveProfiles("parallelEnrichmentTest")
@Slf4j
public class EnrichmentServicesTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MovieDao dao;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    List<EnrichmentService> enrichmentServices;

    public void testEnrichmentServiceImplementation(EnrichmentService service) {
        var movie = getMovie(service, 106);
        assertNotNull(movie.getReviews());
        assertFalse(movie.getReviews().isEmpty());
        assertNotNull(movie.getGenres());
        assertFalse(movie.getGenres().isEmpty());
        assertNotNull(movie.getCountries());
        assertFalse(movie.getCountries().isEmpty());
    }

    public void testEnrichmentServiceImplementationWithTimeout(EnrichmentService service) {
        var movie = getMovie(service, 105);
        assertNotNull(movie.getReviews());
        assertFalse(movie.getReviews().isEmpty());
        assertNotNull(movie.getGenres());
        assertFalse(movie.getGenres().isEmpty());
        assertNull(movie.getCountries());
    }

    @TestFactory
    @DisplayName("Full Enrichment: checks whether parallel enrichment enriches all appropriate Movie fields: genres, countries, reviews")
    public Stream<DynamicTest> getEnrichmentTests() {
        when(countryDao.getCountriesByMovieId(106))
                .thenAnswer(invocationOnMock -> invocationOnMock.callRealMethod());
        return enrichmentServices
                .stream()
                .map(s -> DynamicTest.dynamicTest(s.getClass().getSimpleName(), () -> testEnrichmentServiceImplementation(s)));

    }

    @TestFactory
    @DisplayName("Partial Enrichment: Checks whether parallel enrichment enriches all appropriate Movie fields except Countries due to timeout")
    public Stream<DynamicTest> getEnrichmentTestsWithTimeout() {
        when(countryDao.getCountriesByMovieId(105))
                .thenAnswer(invocationOnMock -> getCountriesWithDelay(invocationOnMock.getArgument(0)));
        return enrichmentServices
                .stream()
                .map(s -> DynamicTest.dynamicTest(s.getClass().getSimpleName(), () -> testEnrichmentServiceImplementationWithTimeout(s)));
    }

    private List<Country> getCountriesWithDelay(long movieId) {
        var countryRowMapper = new CountryRowMapper();
        String getCountriesByMovieIdQuery = "SELECT distinct cntr_id, cntr_name \n" +
                "FROM movie.v_all_movie_countries_ui mc, pg_sleep(6) \n" +
                "WHERE m_id = ?";
        return jdbcTemplate.query(getCountriesByMovieIdQuery, countryRowMapper, movieId);
    }

    private Movie getMovie(EnrichmentService service, long id) {
        var movie = dao.getMovieById(id).get();
        service.enrich(movie);
        return movie;
    }

}