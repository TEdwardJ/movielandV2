package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.web.dto.ReviewDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "DefaultReviewMapper")
public interface ReviewMapper {
    ReviewDto mapToDTO(Review review);
    List<ReviewDto> mapToDTOs(List<Review> movie);
}
