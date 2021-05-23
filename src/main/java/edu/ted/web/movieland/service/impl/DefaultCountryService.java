package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCountryService implements CountryService {

    private final CountryDao countryDao;

    @Override
    public List<Country> getCountriesByMovieId(long movieId) {
        return countryDao.getCountriesByMovieId(movieId);
    }
}
