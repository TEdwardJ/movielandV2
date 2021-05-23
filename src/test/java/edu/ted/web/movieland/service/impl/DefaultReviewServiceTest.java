package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.request.AddReviewRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultReviewServiceTest {
    @Mock
    private ReviewDao dao;

    @InjectMocks
    private DefaultReviewService service;

    @Test
    void addNewReview() {
        Review expectedReview = new Review(105, "Movie 105 Review");
        when(dao.save(any()))
                .thenReturn(expectedReview);
        var newReviewRequest = new AddReviewRequest(105, "Movie 105 Review");
        var returnedReview = service.addNewReview(newReviewRequest);
        assertEquals(returnedReview.getText(), newReviewRequest.getText());
        assertEquals(returnedReview.getMovieId(), newReviewRequest.getMovieId());
    }


    @Test
    void getReviewsByMovieId() {
        when(dao.getReviewsByMovieId(anyLong()))
                .thenReturn(prepareReviewList());
        var reviews = service.getReviewsByMovieId(12L);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
    }

    private List<Review> prepareReviewList() {
        return IntStream
                .rangeClosed(1, 5)
                .mapToObj(i -> new Review(12L, "Review " + i))
                .collect(toList());
    }
}