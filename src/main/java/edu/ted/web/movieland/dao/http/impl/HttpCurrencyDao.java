package edu.ted.web.movieland.dao.http.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ted.web.movieland.dao.http.CurrencyDao;
import edu.ted.web.movieland.entity.ExchangeRate;
import edu.ted.web.movieland.util.CurrencyMapper;
import edu.ted.web.movieland.web.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class HttpCurrencyDao implements CurrencyDao {
    private static final String BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date={date}&json";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<ExchangeRate> getForDate(LocalDate date) {
        String bodyJSON = sendRequestForRates(date);
        try {
            var currencyList = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .readValue(bodyJSON, new TypeReference<List<CurrencyDto>>() {
                    });
            return currencyList
                    .stream()
                    .map(CurrencyMapper::exchangeRateFromDto)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            log.error("Response from NBU parsing error: {}", bodyJSON, e);
        }
        return null;
    }

    private String sendRequestForRates(LocalDate date) {
        Request request = new Request
                .Builder()
                .url(BASE_URL.replace("{date}", date.format(formatter)))
                .build();
        Call call = client.newCall(request);
        try {
            var response = call.execute();
            var bodyJSON = response.body().string();
            response.close();
            return bodyJSON;
        } catch (IOException e) {
            log.error("Attempt to receive rates from NBU failed", e);
        }
        return "";
    }
}
