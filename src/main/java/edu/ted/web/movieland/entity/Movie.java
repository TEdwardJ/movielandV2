package edu.ted.web.movieland.entity;

import lombok.Data;

import java.util.List;

@Data
public class Movie {

    private long id;
    private String russianName;
    private String nativeName;
    private String description;
    private String releaseYear;
    private Double rating;
    private Double price;
    private String pictureUrl;

    private List<Genre> genres;
    private List<Review> reviews;
    private List<Country> countries;
}
