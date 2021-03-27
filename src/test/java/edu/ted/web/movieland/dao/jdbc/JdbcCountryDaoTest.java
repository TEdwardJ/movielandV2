package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.jdbc.impl.JdbcCountryDao;
import edu.ted.web.movieland.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FullSpringNoMvcTest
class JdbcCountryDaoTest {

    @Autowired
    private JdbcCountryDao countryDao;

    @Test
    void getCountriesByMovieId() {
        var countriesList = countryDao.getCountriesByMovieId(109);
        assertFalse(countriesList.isEmpty());
        assertTrue(containsCountry(countriesList, "США"));
        assertTrue(containsCountry(countriesList, "Германия"));
    }

    private boolean containsCountry(List<Country> countriesList, String givenCountry) {
        return countriesList.stream().anyMatch(country -> country.getName().equals(givenCountry));
    }
}