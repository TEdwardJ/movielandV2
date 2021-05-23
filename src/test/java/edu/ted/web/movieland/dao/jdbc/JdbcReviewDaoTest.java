package edu.ted.web.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.dao.jdbc.impl.JdbcReviewDao;
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
        var result = reviewDao.save(review);
        assertTrue(result.getId() > 0);
        assertSame(review, result);

    }

    @Test
    void givenMovieId_whenNonEmptyListOfReviewReceived_thenCorrect(){
        var reviewList = reviewDao.getReviewsByMovieId(106);
        assertFalse(reviewList.isEmpty());
        for (Review review : reviewList) {
            assertFalse(review.getText().isEmpty());
            assertNotEquals(0, review.getMovieId());
            assertNotEquals(0, review.getId());
            assertNotNull(review.getReviewDate());
        }
    }

}