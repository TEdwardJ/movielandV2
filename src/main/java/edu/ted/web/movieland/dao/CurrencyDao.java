package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyDao {
    List<ExchangeRate> getForDate(LocalDate date);
}
