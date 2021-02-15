package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.entity.MovieDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultMovieMapper")
public interface MovieMapper {
    @Mappings({
            @Mapping(target="nameRussian", source="russianName"),
            @Mapping(target="nameNative", source="nativeName"),
            @Mapping(target="picturePath", source="pictureUrl")
    })
    MovieDTO mapToDTO(Movie movie);

    List<MovieDTO> mapToDTOs(List<Movie> movie);
}
