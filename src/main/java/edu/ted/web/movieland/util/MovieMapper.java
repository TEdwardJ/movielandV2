package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.dto.MovieDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultMovieMapper")
public interface MovieMapper {
    MovieDto mapToDTO(Movie movie);

    List<MovieDto> mapToDTOs(List<Movie> movie);
}
