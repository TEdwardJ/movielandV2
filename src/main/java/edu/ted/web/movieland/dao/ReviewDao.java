package edu.ted.web.movieland.dao;

import edu.ted.web.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {

    Review save(Review review);

    List<Review> getReviewsByMovieId(long id);
}
