package edu.ted.web.movieland.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Currency;

@RequiredArgsConstructor
@ToString
public class GetMovieRequest {

    @Getter
    private final long movieId;
    @Getter
    private Currency currency = Currency.getInstance("UAH");

    public void setCurrency(String currency){
        this.currency = Currency.getInstance(currency);
    }
}
