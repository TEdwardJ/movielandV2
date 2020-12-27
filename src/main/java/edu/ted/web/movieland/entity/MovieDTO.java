package edu.ted.web.movieland.entity;

import lombok.Data;

@Data
public class MovieDTO {

    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}
