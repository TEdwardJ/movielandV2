package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var reviewFromDb = new Review(resultSet.getInt("M_ID"), resultSet.getString("MESSAGE"));
        reviewFromDb.setReviewId(resultSet.getInt("REVIEW_ID"));
        reviewFromDb.setReviewDate(resultSet.getDate("REVIEW_DATE").toLocalDate());
        var review_user = new User();
        review_user.setId(resultSet.getInt("USR_ID"));
        reviewFromDb.setUser(review_user);
        return reviewFromDb;
    }
}
