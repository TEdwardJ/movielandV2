package edu.ted.web.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private int id;
    private String name;
}
