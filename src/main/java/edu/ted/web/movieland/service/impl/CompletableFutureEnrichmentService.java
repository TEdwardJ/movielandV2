package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.ReviewService;
import edu.ted.web.movieland.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompletableFutureEnrichmentService implements EnrichmentService {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    <T> CompletableFuture supplyWithCompletableFuture(Supplier<T> supplier, Consumer<T> consumer){
        return CompletableFuture
                .supplyAsync(supplier)
                .orTimeout(5, TimeUnit.SECONDS)
                .thenAccept(consumer);
    }

    @Override
    public void enrich(Movie movie) {
        long movieId = movie.getId();
        var genresFuture = supplyWithCompletableFuture(() ->  new ArrayList<>(movie.getGenres()), movie::setGenres);
        var countryFuture = supplyWithCompletableFuture(() ->  new ArrayList<>(movie.getCountries()), movie::setCountries);
        var reviewFuture = supplyWithCompletableFuture(() -> reviewService.getReviewsByMovieId(movieId), movie::setReviews);
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
