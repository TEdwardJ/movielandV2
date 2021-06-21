package edu.ted.web.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Value
@Entity
@Table(name="genre")
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Genre {

    @EqualsAndHashCode.Exclude
    @Id
    @Column(name="gnr_id")
    int id;
    @Column(name="gnr_name")
    String name;
}
