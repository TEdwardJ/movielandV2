package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.service.UserService;
import edu.ted.web.movieland.service.impl.DefaultReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.comparator.Comparators;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@FullSpringNoMvcTest
class JdbcReviewDaoTest {
    private final String email = "dennis.craig82@example.com";

    @Autowired
    private JdbcReviewDao reviewDao;

    @Autowired
    private UserDao userDao;

    @Test
    void addReview() {
        var review = new Review(105, "Just new 105 Movie Review");
        var user = userDao.findUserByEmail(email);
        review.setUser(user);
        var result = reviewDao.addReview(review);
        assertTrue(result > 0);

        var reviewList = reviewDao.findAllByMovieId(105);
        reviewList.sort(Comparator.comparing(Review::getReviewDate).thenComparing(Review::getReviewId).reversed());
        var addedReview = reviewList.get(0);
        review.setReviewId(addedReview.getReviewId());
        assertTrue(reviewList.contains(review));
    }

    @Test
    void findAllByMovieId() {
        var reviewList = reviewDao.findAllByMovieId(105);
        assertFalse(reviewList.isEmpty());
        var review = reviewList.get(0);
        assertFalse(review.getText().isEmpty());
        assertTrue(review.getReviewId()>0);
        assertNotNull(review.getUser());
        assertTrue(review.getMovieId()>0);
        assertNotNull(review.getReviewDate());
        assertEquals(105, review.getMovieId());
    }
}