package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Review;
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

        when(dao.addReview(any()))
                .thenReturn(111222);
    }

    //@Test
    void getReviewsByMovieId() {
    }

    @Test
    void addNewReview() {
        var newReview = new Review(105, "text105");
        var returnedReview = service.addNewReview(newReview);
        assertEquals(returnedReview.getText(), newReview.getText());
        assertEquals(111222, newReview.getReviewId());
    }
}