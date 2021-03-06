package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.CountryService;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.GenreService;
import edu.ted.web.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ParallelEnrichmentService implements EnrichmentService {

    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;

    private final ExecutorService executors = Executors.newCachedThreadPool();

    private <T> Runnable handleEnrichment(Supplier<T> supplier, Consumer<T> consumer) {
        log.info("Data to be received");
        var suppliedData = supplier.get();
        log.info("Data was supplied {'suppliedData': {}}", suppliedData);
        return () -> consumer.accept(suppliedData);
    }

    @Override
    public void enrich(Movie movie) {
        final long movieId = movie.getId();
        try {
            var futures = executors.invokeAll(List.of(
                    () -> handleEnrichment(() -> genreService.getGenreByMovieId(movieId), movie::setGenres),
                    () -> handleEnrichment(() -> countryService.getCountriesByMovieId(movieId), movie::setCountries),
                    () -> handleEnrichment(() -> reviewService.getReviewsByMovieId(movieId), movie::setReviews)),
                    5, TimeUnit.SECONDS);
            for (Future<Object> future : futures) {
                if (future.isDone() && !future.isCancelled()) {
                    try {
                        ((Runnable) future.get()).run();
                    } catch (ExecutionException e) {
                        log.error("Error during enrichment of movie with id {}", movieId, e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("Interruption error during enrichment of movie with id {}",movieId, e);
        }
    }
}
