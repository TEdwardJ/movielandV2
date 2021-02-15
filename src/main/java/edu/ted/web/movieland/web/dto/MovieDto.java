package edu.ted.web.movieland.web.dto;

import lombok.Data;

@Data
public class MovieDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}
