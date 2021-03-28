package edu.ted.web.movieland.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {
    private long id;
    private String text;
    private UserDto user;
}
