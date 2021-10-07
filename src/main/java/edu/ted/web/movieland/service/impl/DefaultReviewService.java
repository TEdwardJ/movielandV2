package edu.ted.web.movieland.service.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.request.AddReviewRequest;
import edu.ted.web.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {

    private final ReviewDao dao;

    @Override
    public Review addNewReview(AddReviewRequest reviewRequest) {
        var reviewToBeAdded = new Review(reviewRequest.getMovieId(), reviewRequest.getText());
        reviewToBeAdded.setUser(reviewRequest.getUser());
        var newReview = dao.save(reviewToBeAdded);
        return newReview;
    }

    @Override
    public List<Review> getReviewsByMovieId(long id) {
        return dao.getReviewsByMovieId(id);
    }

}
