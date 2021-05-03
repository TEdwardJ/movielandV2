package edu.ted.web.movieland.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.ted.web.movieland.dao.CurrencyDao;
import edu.ted.web.movieland.entity.ExchangeRate;
import edu.ted.web.movieland.service.CurrencyService;
import edu.ted.web.movieland.util.Converters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CachedCurrencyService implements CurrencyService {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("UAH");
    private LoadingCache<LocalDate, List<ExchangeRate>> ratesCache;

    private final CurrencyDao dao;

    List<ExchangeRate> getForDate(LocalDate date) {
        return ratesCache.get(date);
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(LocalDate date, Currency currency) {
        return getForDate(date)
                .stream()
                .filter(er->er.getTo().equals(currency))
                .findFirst();
    }

    public double convert(double amount, LocalDate date, Currency toCurrency){
        return getExchangeRate(date, toCurrency)
                .map(rate -> convert(amount, rate))
                .orElse(amount);
    }

    public double convert(double amount, ExchangeRate rate){
        return Converters.getConverter(rate).convert(amount);
    }

    public double convert(double amount, Converters.CurrencyConverter converter){
        return converter.convert(amount);
    }


    @PostConstruct
    protected void initCache(){
        this.ratesCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(date -> Collections.unmodifiableList(dao.getForDate(date)));
    }

    public static Currency getDefaultCurrency(){
        return DEFAULT_CURRENCY;
    }
}
