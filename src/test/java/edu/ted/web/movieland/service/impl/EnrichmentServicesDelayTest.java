package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.util.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
@ActiveProfiles("parallelEnrichmentTest")
@Slf4j
public class EnrichmentServicesDelayTest {

    @Autowired
    private MovieDao dao;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper mapper;

    @Autowired
    List<EnrichmentService> enrichmentServices;

    public void testEnrichmentServiceImplementationWithTimeout(EnrichmentService service) {
        movieService.setEnrichmentService(service);
        var movie = getMovie(service, 105L);
        var movieDto = mapper.mapToDto(movie);

        assertNotNull(movieDto.getGenres());
        assertFalse(movieDto.getGenres().isEmpty());
        assertNotNull(movieDto.getReviews());
        assertNull(movieDto.getCountries());
        assertFalse(movieDto.getReviews().isEmpty());
    }

    @TestFactory
    @DisplayName("Partial Enrichment: Checks whether parallel enrichment enriches all appropriate Movie fields except Countries due to timeout")
    public Stream<DynamicTest> getEnrichmentTestsWithTimeout() {
        return enrichmentServices
                .stream()
                .map(service -> DynamicTest.dynamicTest(service.getClass().getSimpleName(), () -> testEnrichmentServiceImplementationWithTimeout(service)));
    }

    private List<Country> justDelay(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Movie getMovie(EnrichmentService service, long id) {
        var movie = movieService.getMovieById(new GetMovieRequest(id)).get();
        Movie spiedMovie = Mockito.spy(movie);
        Mockito.doAnswer(invocationOnMock -> justDelay()).when(spiedMovie).getCountries();
        return spiedMovie;
    }

}