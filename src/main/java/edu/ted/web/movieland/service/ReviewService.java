package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.request.AddReviewRequest;

public interface ReviewService {

    Review addNewReview(AddReviewRequest review);
}
