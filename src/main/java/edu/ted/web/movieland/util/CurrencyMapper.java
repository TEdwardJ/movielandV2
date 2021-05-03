package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.ExchangeRate;
import edu.ted.web.movieland.service.impl.CachedCurrencyService;
import edu.ted.web.movieland.web.dto.CurrencyDto;

import java.util.Currency;

public class CurrencyMapper {

    public static ExchangeRate exchangeRateFromDto(CurrencyDto currency) {
        var defaultCurrency = CachedCurrencyService.getDefaultCurrency();
        var toCurrency = Currency.getInstance(currency.getShortName());
        return new ExchangeRate(currency.getExchangeLocalDate(), defaultCurrency, toCurrency, currency.getRate());
    }
}
