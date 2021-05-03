package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final MovieDao dao;
    private final GenreDao genreDao;
    private final CountryDao countryDao;
    private final ReviewDao reviewDao;

    private final CachedCurrencyService currencyService;

    public List<Movie> findAll(MovieRequest request) {
        return dao.findAll(request.getSorting());
    }

    public List<Movie> getNRandomMovies(int count) {
        return dao.getNRandomMovies(count);
    }

    public List<Movie> getMoviesByGenre(int genreId, MovieRequest request) {
        return dao.getMoviesByGenre(genreId, request.getSorting());
    }

    @Override
    public Optional<Movie> getMovieById(GetMovieRequest request) {
        return dao
                .getMovieById(request.getMovieId())
                .stream()
                .peek(this::enrichMovie)
                .peek(movie -> convertCurrency(movie, request.getCurrency()))
                .findFirst();
    }

    private void convertCurrency(Movie movie, Currency currency) {
        var convertedAmount = currencyService.convert(movie.getPrice(), LocalDate.now(), currency);
        movie.setPrice(convertedAmount);
    }

    private void enrichMovie(Movie movie) {
        long movieId = movie.getId();

        var genresFuture = CompletableFuture
                .supplyAsync(() -> genreDao.getGenreByMovieId(movieId))
                .thenAccept(movie::setGenres);
        var countryFuture = CompletableFuture
                .supplyAsync(() -> countryDao.getCountriesByMovieId(movieId))
                .thenAccept(movie::setCountries);
        var reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewDao.getReviewsByMovieId(movieId))
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
