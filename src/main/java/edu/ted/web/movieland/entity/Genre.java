package edu.ted.web.movieland.entity;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class Genre {
    @EqualsAndHashCode.Exclude
    int id;
    String name;
}
