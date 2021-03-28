package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.request.AddReviewRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultReviewServiceTest {
    @Mock
    private ReviewDao dao;

    private DefaultReviewService service;

    @BeforeEach
    public void init() {
        service = new DefaultReviewService(dao);
        Review expectedReview = new Review(105, "Movie 105 Review");
        when(dao.save(any()))
                .thenReturn(expectedReview);
    }


    @Test
    void addNewReview() {
        var newReviewRequest = new AddReviewRequest(105, "Movie 105 Review");
        var returnedReview = service.addNewReview(newReviewRequest);
        assertEquals(returnedReview.getText(), newReviewRequest.getText());
        assertEquals(returnedReview.getMovieId(), newReviewRequest.getMovieId());
    }
}