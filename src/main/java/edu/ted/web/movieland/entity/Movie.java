package edu.ted.web.movieland.entity;

import lombok.Data;

@Data
public class Movie {

    private int id;
    private String title;
    private String titleEng;
    private String description;
    private String releaseYear;
    private Double rating;
    private Double price;
    private String pictureUrl;
}
