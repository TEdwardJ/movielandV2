package edu.ted.web.movieland.web.dto;

import lombok.Data;

@Data
public class MovieDto {

    private int id;
    private String russianName;
    private String nativeName;
    private String releaseYear;
    private Double rating;
    private Double price;
    private String pictureUrl;
    private String description;
}
