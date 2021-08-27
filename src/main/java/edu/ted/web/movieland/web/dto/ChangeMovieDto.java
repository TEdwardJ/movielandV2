package edu.ted.web.movieland.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class ChangeMovieDto {

    private int id;
    @JsonAlias("nameRussian")
    private String russianName;
    @JsonAlias("nameNative")
    private String nativeName;
    @JsonAlias("yearOfRelease")
    private String releaseYear;
    private Double rating;
    @JsonAlias("price")
    private Double price;
    @JsonAlias("picturePath")
    private String pictureUrl;
    @JsonAlias("description")
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> countries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> genres;

}
