package edu.ted.web.movieland.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties
public class CurrencyDto {
    @JsonProperty("r030")
    private int isoCode;
    @JsonProperty("txt")
    private String name;
    @JsonProperty("rate")
    private double rate;
    @JsonProperty("cc")
    private String shortName;
    @JsonProperty("exchangedate")
    private Date exchangeDate;

    public CurrencyDto() {
    }

    public CurrencyDto(int isoCode, double rate, String shortName) {
        this.isoCode = isoCode;
        this.rate = rate;
        this.shortName = shortName;
    }

    @JsonIgnore
    public int getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getShortName() {
        return shortName;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }
    public LocalDate getExchangeLocalDate() {
        return exchangeDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDto currency = (CurrencyDto) o;
        return getIsoCode() == currency.getIsoCode() &&
                name.equals(currency.name) &&
                shortName.equals(currency.shortName) &&
                exchangeDate.equals(currency.exchangeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsoCode(), name, shortName, exchangeDate);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "isoCode=" + isoCode +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", shortName='" + shortName + '\'' +
                ", excahngeDate=" + exchangeDate +
                '}';
    }
}

