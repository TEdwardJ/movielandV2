package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.CountryService;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.GenreService;
import edu.ted.web.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompletableFutureEnrichmentService implements EnrichmentService {

    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;

    <T> CompletableFuture supplyWithCompletableFuture(Supplier<T> supplier, Consumer<T> consumer){
        return CompletableFuture
                .supplyAsync(supplier)
                .orTimeout(5, TimeUnit.SECONDS)
                .thenAccept(consumer);
    }

    @Override
    public void enrich(Movie movie) {
        long movieId = movie.getId();
        var genresFuture = supplyWithCompletableFuture(() -> genreService.getGenreByMovieId(movieId), movie::setGenres);
        var countryFuture = supplyWithCompletableFuture(() -> countryService.getCountriesByMovieId(movieId), movie::setCountries);
        var reviewFuture = supplyWithCompletableFuture(() ->reviewService.getReviewsByMovieId(movieId), movie::setReviews);
        log.debug("Enrichment process is about to be started for movie with id {'movieId': {}}", movieId);
        CompletableFuture
                .allOf(genresFuture, countryFuture, reviewFuture)
                .completeOnTimeout(null, 5, TimeUnit.SECONDS)
                .exceptionally(this::handleException)
                .join();
    }

    private Void handleException(Throwable e) {
        log.error("Movie can be enriched partially or not enriched at all, since the error raised during movie enrichment process: ", e);
        return null;
    }
}
