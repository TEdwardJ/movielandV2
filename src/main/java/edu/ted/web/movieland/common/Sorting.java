package edu.ted.web.movieland.common;

import edu.ted.web.movieland.common.OrderByColumn;
import edu.ted.web.movieland.common.OrderDirection;
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
