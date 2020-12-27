package edu.ted.web.movieland.utils;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.entity.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public interface MovieMapper {
    @Mappings({
            @Mapping(target="nameRussian", source="title"),
            @Mapping(target="nameNative", source="titleEng"),
            @Mapping(target="picturePath", source="pictureUrl")
    })
    MovieDTO mapToDTO(Movie movie);

    default List<MovieDTO> movieListToMovieDTOList(List<Movie> movies){
        List<MovieDTO> list = new ArrayList<>();
        for (Movie movie : movies) {
            list.add(mapToDTO(movie));

        }
        return list;
    }
}
