package edu.ted.web.movieland.utils;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.entity.MovieDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface MovieMapper {
    @Mappings({
            @Mapping(target="nameRussian", source="title"),
            @Mapping(target="nameNative", source="titleEng"),
            @Mapping(target="picturePath", source="pictureUrl")
    })
    MovieDTO mapToDTO(Movie movie);

    List<MovieDTO> mapToDTOs(List<Movie> movie);
}
