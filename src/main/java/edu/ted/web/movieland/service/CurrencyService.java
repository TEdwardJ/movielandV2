package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

public interface CurrencyService {
    Optional<ExchangeRate> getExchangeRate(LocalDate date, Currency currency);
    double convert(double amount, LocalDate date, Currency toCurrency);
}
