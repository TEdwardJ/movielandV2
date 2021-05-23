package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.request.AddReviewRequest;

import java.util.List;

public interface ReviewService {

    Review addNewReview(AddReviewRequest review);

    List<Review> getReviewsByMovieId(long id);
}
