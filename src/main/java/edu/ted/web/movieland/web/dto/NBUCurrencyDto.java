package edu.ted.web.movieland.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@JsonIgnoreProperties
public class NBUCurrencyDto {
    @JsonProperty("currencyCode")
    private int isoCode;
    @JsonProperty("txt")
    private String name;
    @JsonProperty("Amount")
    private double rate;
    @JsonProperty("currencyCodeL")
    private String shortName;
    @JsonProperty("startDate")
    private Date exchangeDate;

    public NBUCurrencyDto(int isoCode, double rate, String shortName) {
        this.isoCode = isoCode;
        this.rate = rate;
        this.shortName = shortName;
    }

    @JsonIgnore
    public int getIsoCode() {
        return isoCode;
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
        NBUCurrencyDto currency = (NBUCurrencyDto) o;
        return getIsoCode() == currency.getIsoCode() &&
                //name.equals(currency.name) &&
                shortName.equals(currency.shortName) &&
                exchangeDate.equals(currency.exchangeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsoCode(), shortName, exchangeDate);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "isoCode=" + isoCode +
//                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", shortName='" + shortName + '\'' +
                ", excahngeDate=" + exchangeDate +
                '}';
    }
}

