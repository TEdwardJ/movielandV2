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


@Service
@RequiredArgsConstructor
@Slf4j
public class CompletableFutureEnrichmentService implements EnrichmentService {

    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;

    @Override
    public void enrich(Movie movie) {
        long movieId = movie.getId();

        var genresFuture = CompletableFuture
                .supplyAsync(() -> genreService.getGenreByMovieId(movieId))
                .thenAccept(movie::setGenres);
        var countryFuture = CompletableFuture
                .supplyAsync(() -> countryService.getCountriesByMovieId(movieId))
                .thenAccept(movie::setCountries);
        var reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewService.getReviewsByMovieId(movieId))
                .thenAccept(movie::setReviews);
        var enrichFuture = CompletableFuture
                .allOf(genresFuture, countryFuture, reviewFuture)
                .exceptionally(this::handleException)
                .completeOnTimeout(null, 5, TimeUnit.SECONDS)
                .join();
    }

    private Void handleException(Throwable e) {
        log.error("Error raised during movie enrichment process: ", e);
        return null;
    }
}
