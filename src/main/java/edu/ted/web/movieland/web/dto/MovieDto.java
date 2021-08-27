package edu.ted.web.movieland.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Genre;
import lombok.Data;

import java.util.List;

@Data
public class MovieDto {

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
    private List<Country> countries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ReviewDto> reviews;

}
