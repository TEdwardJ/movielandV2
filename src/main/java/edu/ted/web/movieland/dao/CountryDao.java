package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> getCountriesByMovieId(long movieId);
}
