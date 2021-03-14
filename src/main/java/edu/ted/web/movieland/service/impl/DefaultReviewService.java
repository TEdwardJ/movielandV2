package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {

    private final ReviewDao dao;

    @Override
    public List<Review> getReviewsByMovieId(int movieId) {
        return dao.findAllByMovieId(movieId);
    }

    @Override
    public Review addNewReview(Review review) {
        int newReviewId = dao.addReview(review);
        review.setReviewId(newReviewId);
        return review;
    }
}
