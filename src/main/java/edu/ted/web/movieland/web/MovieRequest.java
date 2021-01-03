package edu.ted.web.movieland.web;

import lombok.Data;

@Data
public class MovieRequest {
    private OrderByColumn orderedColumn;
    private OrderDirection orderDirection;

    public MovieRequest(OrderByColumn orderedColumn, OrderDirection orderDirection) {
        this.orderedColumn = orderedColumn;
        this.orderDirection = orderDirection;
    }
}
