package edu.ted.web.movieland.web.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private long id;
    private String text;
    private UserDto user;
}
