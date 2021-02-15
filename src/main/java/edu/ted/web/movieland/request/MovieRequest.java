package edu.ted.web.movieland.request;

import edu.ted.web.movieland.common.Sorting;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MovieRequest {
    @Getter
    private Sorting sorting;

    public MovieRequest(String orderedColumn, String orderDirection) {
        sorting = new Sorting(orderedColumn, orderDirection);
    }
}
