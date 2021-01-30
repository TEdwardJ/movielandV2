package edu.ted.web.movieland.web.entity;

import lombok.Getter;

public class Sorting {
    @Getter
    private OrderByColumn orderedColumn;
    @Getter
    private OrderDirection orderDirection;

    public Sorting(OrderByColumn orderedColumn, OrderDirection orderDirection) {
        this.orderedColumn = orderedColumn;
        this.orderDirection = orderDirection;
    }
}
