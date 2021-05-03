package edu.ted.web.movieland.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
public class ExchangeRate {
    @Getter
    private final LocalDate exchangeRateDate;
    @Getter
    private final java.util.Currency from;
    @Getter
    private final  java.util.Currency to;
    @Getter
    private final double rate;


}
