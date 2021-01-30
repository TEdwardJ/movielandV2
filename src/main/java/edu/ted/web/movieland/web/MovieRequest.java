package edu.ted.web.movieland.web;

import edu.ted.web.movieland.web.entity.OrderByColumn;
import edu.ted.web.movieland.web.entity.OrderDirection;
import edu.ted.web.movieland.web.entity.Sorting;
import lombok.Setter;

public class MovieRequest {
    @Setter
    private OrderByColumn orderedColumn;
    @Setter
    private OrderDirection orderDirection;
    private Sorting sorting;

    public MovieRequest() {
    }

    public MovieRequest(String orderedColumn, String orderDirection) {
        this.orderedColumn = OrderByColumn.validateEnumAndReturn(orderedColumn);
        this.orderDirection = OrderDirection.validateEnumAndReturn(orderDirection);

    }

    public Sorting getSorting() {
        if (sorting == null){
            setDefaults();
            if (orderedColumn != null && orderDirection != null) {
                sorting = new Sorting(orderedColumn, orderDirection);
            }
        }
        return sorting;
    }

    private void setDefaults() {
        if (orderedColumn == OrderByColumn.RATING && orderDirection == null) {
            orderDirection = OrderDirection.DESC;
        }
    }
}
