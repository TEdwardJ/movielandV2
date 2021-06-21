package edu.ted.web.movieland.dao.http.impl;

import edu.ted.web.movieland.dao.http.CurrencyDao;
import edu.ted.web.movieland.entity.ExchangeRate;
import edu.ted.web.movieland.util.CurrencyMapper;
import edu.ted.web.movieland.web.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public class HttpCurrencyDao implements CurrencyDao {
    private static final String BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date={date}&json";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ExchangeRate> getForDate(LocalDate date) {
        String fooResourceUrl = BASE_URL.replace("{date}", date.format(formatter));
        try {
            ResponseEntity<List<CurrencyDto>> response = restTemplate.exchange(fooResourceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {});
            return response
                    .getBody()
                    .stream()
                    .map(CurrencyMapper::exchangeRateFromDto)
                    .collect(toList());
        } catch (RestClientException e) {
            log.error("Attempt to receive rates from NBU failed", e);
            return null;
        }
    }

}
