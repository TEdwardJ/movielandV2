package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.util.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
@ActiveProfiles("parallelEnrichmentTest")
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnrichmentServicesTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper mapper;

    @Autowired
    List<EnrichmentService> enrichmentServices;

    public void testEnrichmentServiceImplementation(EnrichmentService service) {
        movieService.setEnrichmentService(service);
        var movie = getMovie(service, 106);

        assertNotNull(movie.getGenres());
        assertFalse(movie.getGenres().isEmpty());
        assertNotNull(movie.getCountries());
        assertFalse(movie.getCountries().isEmpty());
        assertNotNull(movie.getReviews());
        assertFalse(movie.getReviews().isEmpty());
    }

    @TestFactory
    @DisplayName("Full Enrichment: checks whether parallel enrichment enriches all appropriate Movie fields: genres, countries, reviews")
    Stream<DynamicTest> getEnrichmentTests() {
        return enrichmentServices
                .stream()
                .map(service -> DynamicTest.dynamicTest(service.getClass().getSimpleName(), () -> testEnrichmentServiceImplementation(service)));
    }

    private Movie getMovie(EnrichmentService service, long id) {
        return movieService.getMovieById(new GetMovieRequest(id)).get();
    }

}