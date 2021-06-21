package edu.ted.web.movieland.common;

import lombok.Value;

@Value
public class Sorting {
    OrderByColumn orderedColumn;
    OrderDirection orderDirection;

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
