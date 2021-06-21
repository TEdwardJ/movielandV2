package edu.ted.web.movieland.entity;

import lombok.*;

import java.time.LocalDate;

@Data
public class Review {
    private long id;
    private final long movieId;
    private final String text;
    private LocalDate reviewDate = LocalDate.now();
    @EqualsAndHashCode.Exclude
    private User user;
}
