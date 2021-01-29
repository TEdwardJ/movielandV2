package edu.ted.web.movieland.web;

import edu.ted.web.movieland.web.entity.OrderByColumn;
import edu.ted.web.movieland.web.entity.OrderDirection;
import edu.ted.web.movieland.web.entity.Sorting;
import lombok.Data;

@Data
public class MovieRequest {
    private OrderByColumn orderedColumn;
    private OrderDirection orderDirection;
    private Sorting sorting;

    public MovieRequest() {
    }

    public MovieRequest(OrderByColumn orderedColumn, OrderDirection orderDirection) {
        this.orderedColumn = orderedColumn;
        this.orderDirection = orderDirection;

    }

    public Sorting getSorting() {
        if (orderedColumn != null && orderDirection != null) {
            if (orderedColumn == OrderByColumn.RATING && orderDirection == null) {
                return new Sorting(orderedColumn, OrderDirection.DESC);
            }
            return new Sorting(orderedColumn, orderDirection);
        }
        return null;
    }
}
