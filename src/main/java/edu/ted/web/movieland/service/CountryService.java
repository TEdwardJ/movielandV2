package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getCountriesByMovieId(long movieId);
}
