package edu.ted.web.movieland.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name="genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "genres-region")
@BatchSize(size=20)
@Immutable
public class Genre {

    @Id
    @Column(name="gnr_id")
    long id;
    @Column(name="gnr_name")
    String name;

}
