package edu.ted.web.movieland.common;

import lombok.Getter;

public class Sorting {
    @Getter
    private final OrderByColumn orderedColumn;
    @Getter
    private final OrderDirection orderDirection;

    public Sorting(String orderedColumn, String orderDirection) {
        OrderDirection orderDirectionTemp;
        this.orderedColumn = OrderByColumn.validateEnumAndReturn(orderedColumn);
        orderDirectionTemp = OrderDirection.validateEnumAndReturn(orderDirection);
        if (this.orderedColumn == OrderByColumn.RATING && orderDirectionTemp == null) {
            orderDirectionTemp = OrderDirection.DESC;
        }
        this.orderDirection = orderDirectionTemp;
    }

}
