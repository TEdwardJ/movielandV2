package edu.ted.web.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "v_movie")
@SecondaryTable(name = "movie_poster", pkJoinColumns = @PrimaryKeyJoinColumn(name = "m_id"))
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_m_id_seq")
    @Column(name = "m_id")
    private long id;
    @Column(name = "m_russian_name")
    private String russianName;
    @Column(name = "m_native_name")
    private String nativeName;
    @Column(name = "m_description")
    private String description;
    @Column(name = "m_release_year")
    private String releaseYear;
    @Column(name = "m_rating")
    private Double rating;
    @Column(name = "m_price")
    private Double price;
    @Column(table="movie_poster", name = "picture_url")
    private String pictureUrl;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = {@JoinColumn(name = "m_id")},
            inverseJoinColumns = {@JoinColumn(name = "gnr_id")})
    private List<Genre> genres;
    @Transient
    @ToString.Exclude
    private List<Review> reviews;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "movie_country",
            joinColumns = {@JoinColumn(name = "m_id")},
            inverseJoinColumns = {@JoinColumn(name = "cntr_id")})
    private List<Country> countries;

    public Movie(long id, String russianName, String nativeName, String description, String releaseYear, Double rating, Double price, String pictureUrl) {
        this.id = id;
        this.russianName = russianName;
        this.nativeName = nativeName;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

}
