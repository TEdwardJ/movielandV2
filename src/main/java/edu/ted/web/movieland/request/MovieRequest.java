package edu.ted.web.movieland.request;

import edu.ted.web.movieland.common.Sorting;
import lombok.Getter;

public class MovieRequest {
    @Getter
    private final Sorting sorting;

    public MovieRequest() {
        sorting = new Sorting(null, null);
    }

    public MovieRequest(String orderedColumn, String orderDirection) {
        sorting = new Sorting(orderedColumn, orderDirection);
    }
}
