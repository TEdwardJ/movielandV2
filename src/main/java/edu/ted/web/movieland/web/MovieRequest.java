package edu.ted.web.movieland.web;

import edu.ted.web.movieland.common.OrderByColumn;
import edu.ted.web.movieland.common.OrderDirection;
import edu.ted.web.movieland.common.Sorting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MovieRequest {
    @Setter
    private OrderByColumn orderedColumn;
    @Setter
    private OrderDirection orderDirection;
    @Getter
    private Sorting sorting;

    public MovieRequest(String orderedColumn, String orderDirection) {
        this.orderedColumn = OrderByColumn.validateEnumAndReturn(orderedColumn);
        this.orderDirection = OrderDirection.validateEnumAndReturn(orderDirection);
        setDefaults();
        if (orderedColumn != null && orderDirection != null) {
            sorting = new Sorting(this.orderedColumn, this.orderDirection);
        }
    }

    private void setDefaults() {
        if (orderedColumn == OrderByColumn.RATING && orderDirection == null) {
            orderDirection = OrderDirection.DESC;
        }
    }
}
