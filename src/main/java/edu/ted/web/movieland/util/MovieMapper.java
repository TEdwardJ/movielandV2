package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.dto.ChangeMovieDto;
import edu.ted.web.movieland.web.dto.MovieDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultMovieMapper")
public interface MovieMapper {
    MovieDto mapToDTO(Movie movie);

    Movie mapToMovie(MovieDto movie);

    Movie mapChangeMovieDtoToMovie(ChangeMovieDto movie);

    ChangeMovieDto mapMovieToChangeMovieDto(Movie movieDto);

    List<Long> mapCountryListToIdList(List<Country> value);

    List<Long> mapGenreListToIdList(List<Genre> value);

    default Long mapCountryToId(Country value) {
        return value.getId();
    }

    default Long mapGenreToId(Genre value) {
        return value.getId();
    }

    default List<Country> mapToCountryList(List<Long> countries) {
        if (Objects.isNull(countries)) {
            return List.of();
        }
        return countries
                .stream()
                .map(id -> new Country(id, null))
                .collect(toList());
    }

    default List<Genre> mapToGenreList(List<Long> genres) {
        if (Objects.isNull(genres)) {
            return List.of();
        }
        return genres
                .stream()
                .map(id -> new Genre(id.intValue(), null))
                .collect(toList());
    }

    List<MovieDto> mapToDTOs(List<Movie> movie);
}
