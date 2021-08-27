package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.request.GetMovieRequest;
import edu.ted.web.movieland.service.EnrichmentService;
import edu.ted.web.movieland.service.MovieService;
import edu.ted.web.movieland.request.MovieRequest;
import edu.ted.web.movieland.util.MovieMapper;
import edu.ted.web.movieland.web.dto.ChangeMovieDto;
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

    private final EnrichmentService enrichmentService;
    private final MovieMapper movieMapper;
    private final MovieDao dao;

    private final EnrichmentService enrichService;

    private final ExecutorService executors = Executors.newCachedThreadPool();

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
                .peek(movie -> convertCurrency(movie, request.getCurrency()))
                .peek(this::enrichMovie)
                .findFirst();
    }

    @Override
    public Movie saveOrUpdate(ChangeMovieDto movie) {
        return dao.saveOrUpdate(movieMapper.mapChangeMovieDtoToMovie(movie));
    }

    private void convertCurrency(Movie movie, Currency currency) {
        var convertedAmount = currencyService.convert(movie.getPrice(), LocalDate.now(), currency);
        movie.setPrice(convertedAmount);
    }

    private void enrichMovie(Movie movie) {
        enrichmentService.enrich(movie);
    }


}
