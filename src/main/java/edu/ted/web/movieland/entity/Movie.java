package edu.ted.web.movieland.entity;

import lombok.Data;

@Data
public class Movie {

    private int id;
    private String russianName;
    private String nativeName;
    private String description;
    private String releaseYear;
    private Double rating;
    private Double price;
    private String pictureUrl;
}
