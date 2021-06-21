package edu.ted.web.movieland.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(schema = "movie", name="v_movie_ui_v2")
public class Movie {

    @Id
    @Column(name="m_id")
    private long id;
    @Column(name="m_russian_name")
    private String russianName;
    @Column(name="m_native_name")
    private String nativeName;
    @Column(name="m_description")
    private String description;
    @Column(name="m_release_year")
    private String releaseYear;
    @Column(name="m_rating")
    private Double rating;
    @Column(name="m_price")
    private Double price;
    @Column(name="picture_url")
    private String pictureUrl;

    @ManyToMany()
    @JoinTable(name="movie_genre")
    private List<Genre> genres;
    @Transient
    private List<Review> reviews;
    @Transient
    private List<Country> countries;

}
