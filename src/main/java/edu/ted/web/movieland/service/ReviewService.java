package edu.ted.web.movieland.service;

import edu.ted.web.movieland.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByMovieId(int movieId);

    Review addNewReview(Review review);
}
