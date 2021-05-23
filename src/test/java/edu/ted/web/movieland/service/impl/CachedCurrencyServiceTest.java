package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.http.CurrencyDao;
import edu.ted.web.movieland.entity.ExchangeRate;
import edu.ted.web.movieland.util.CurrencyMapper;
import edu.ted.web.movieland.web.dto.CurrencyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@FullSpringNoMvcTest
class CachedCurrencyServiceTest {

    @Mock
    private CurrencyDao dao;
    @Autowired
    @InjectMocks
    private CachedCurrencyService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        List<CurrencyDto> ratesList = List.of(new CurrencyDto(840, 27, "USD"), new CurrencyDto(978, 33, "EUR"));
        List<ExchangeRate> rates = ratesList.stream()
                .map(CurrencyMapper::exchangeRateFromDto)
                .collect(Collectors.toList());
        when(dao.getForDate(any())).thenReturn(rates);
    }

    @Test
    void getForDate() {
        var rates = service.getForDate(LocalDate.now());
        assertFalse(rates.isEmpty());
    }

    @Test
    void getForDateTwice_whenListTheSameObject_thenCorrect() {
        var rates1 = service.getForDate(LocalDate.now());
        var rates2 = service.getForDate(LocalDate.now());
        assertFalse(rates1.isEmpty());
        assertFalse(rates2.isEmpty());
        assertSame(rates1, rates2);
    }
}