package edu.ted.web.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import static org.junit.jupiter.api.Assertions.*;

@DBRider
@FullSpringNoMvcTest
class JdbcReviewDaoTest {
    @Value("${testUser.email}")
    private String email;

    @Autowired
    private JdbcReviewDao reviewDao;

    @Autowired
    private UserDao userDao;

    @Test
    @DBUnit(schema = "movie")
    @ExpectedDataSet(value = "addedReview.yml", compareOperation = CompareOperation.CONTAINS)
    void addReviewDBRiderTest() {
        var review = new Review(105, "Just the newest 105 Movie Review");
        var user = userDao.findUserByEmail(email);
        review.setUser(user.get());
        var result = reviewDao.addReview(review);
        assertTrue(result > 0);

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