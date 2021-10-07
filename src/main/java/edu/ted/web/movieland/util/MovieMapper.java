package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.Country;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import edu.ted.web.movieland.web.dto.ChangeMovieDto;
import edu.ted.web.movieland.web.dto.MovieDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultMovieMapper")
public interface MovieMapper {
    MovieDto mapToDto(Movie movie);

    Movie mapChangeMovieDtoToMovie(ChangeMovieDto movie);

    ChangeMovieDto mapMovieToChangeMovieDto(Movie movieDto);

    default Long mapCountryToId(Country value) {
        return value.getId();
    }

    default Long mapGenreToId(Genre value) {
        return value.getId();
    }

    @Mappings(@Mapping(target = "id", source = "countryId"))
    Country longToCountry(Long countryId);

    @Mappings(@Mapping(target = "id", source = "genreId"))
    Genre longToGenre(Long genreId);

    List<Country> mapToCountryList(List<Long> countries);

    List<Genre> mapToGenreList(List<Long> genres);

    List<Long> mapCountryListToIdList(List<Country> value);

    List<Long> mapGenreListToIdList(List<Genre> value);

    List<MovieDto> mapToDTOs(List<Movie> movie);
}
