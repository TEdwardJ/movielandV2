package edu.ted.web.movieland.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Review;
import lombok.Data;

import java.util.List;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Country> countries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;
}
