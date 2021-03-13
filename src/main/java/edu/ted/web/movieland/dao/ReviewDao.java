package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {

    int addReview(Review review);
    List<Review> findAllByMovieId(int movieId);
}
