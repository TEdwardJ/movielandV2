package edu.ted.web.movieland.common;

import edu.ted.web.movieland.common.OrderByColumn;
import edu.ted.web.movieland.common.OrderDirection;
import lombok.Getter;

public class Sorting {
    @Getter
    private final OrderByColumn orderedColumn;
    @Getter
    private OrderDirection orderDirection;

    public Sorting(String orderedColumn, String orderDirection) {
        this.orderedColumn = OrderByColumn.validateEnumAndReturn(orderedColumn);
        this.orderDirection = OrderDirection.validateEnumAndReturn(orderDirection);
        setDefaults();
    }

    private void setDefaults() {
        if (orderedColumn == OrderByColumn.RATING && orderDirection == null) {
            orderDirection = OrderDirection.DESC;
        }
    }
}
