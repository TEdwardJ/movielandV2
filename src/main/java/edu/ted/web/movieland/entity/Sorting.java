package edu.ted.web.movieland.entity;

import edu.ted.web.movieland.web.OrderByColumn;
import edu.ted.web.movieland.web.OrderDirection;
import lombok.Getter;

public class Sorting {
    @Getter
    private OrderByColumn column;
    @Getter
    private OrderDirection direction;

    public Sorting(OrderByColumn column, OrderDirection direction) {
        this.column = column;
        this.direction = direction;
    }
}
