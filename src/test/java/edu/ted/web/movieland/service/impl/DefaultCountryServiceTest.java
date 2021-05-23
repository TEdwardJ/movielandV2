package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import edu.ted.web.movieland.dao.CountryDao;
import edu.ted.web.movieland.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NoWebSpringTestConfiguration.class})
class DefaultCountryServiceTest {

    @Mock
    private CountryDao dao;

    @InjectMocks
    private DefaultCountryService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(dao.getCountriesByMovieId(anyLong()))
                .thenReturn(prepareCountryList());
    }

    @Test
    void getCountriesByMovieId() {
        var countriesList = service.getCountriesByMovieId(12L);
        assertNotNull(countriesList);
        assertFalse(countriesList.isEmpty());
    }

    private List<Country> prepareCountryList() {
        return IntStream
                .rangeClosed(1, 5)
                .mapToObj(i -> new Country(i, "Country " + i))
                .collect(toList());
    }
}