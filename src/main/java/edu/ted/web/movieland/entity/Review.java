package edu.ted.web.movieland.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Review {
    @Getter
    @Setter
    private long reviewId;
    @Getter
    private final long movieId;
    @Getter
    private final String text;
    @Getter
    @Setter
    private LocalDate reviewDate = LocalDate.now();
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private User user;
}
