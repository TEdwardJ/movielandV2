package edu.ted.web.movieland.dao.jdbc.impl;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.dao.jdbc.mapper.ReviewRowMapper;
import edu.ted.web.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReviewDao implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert reviewInsert;
    private final String movieReviewsQuery;
    private final RowMapper<Review> reviewMapper = new ReviewRowMapper();

    public JdbcReviewDao(DataSource dataSource, JdbcTemplate jdbcTemplate, @Value("${getMovieReviewsQuery}") String movieReviewsQuery) {
        this.reviewInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName("movie")
                .withTableName("movie_Review")
                .usingGeneratedKeyColumns("review_id");

        this.jdbcTemplate = jdbcTemplate;
        this.movieReviewsQuery = movieReviewsQuery;
    }

    @Override
    public Review save(Review review) {
        Map<String, Object> parameters = Map.of(
                "USR_ID", review.getUser().getId(),
                "MESSAGE", review.getText(),
                "M_ID", review.getMovieId(),
                "REVIEW_DATE", review.getReviewDate());
        var key = reviewInsert.executeAndReturnKey(parameters);
        review.setId(key.intValue());
        return review;
    }

    @Override
    public List<Review> getReviewsByMovieId(long id) {
        return jdbcTemplate.query(movieReviewsQuery, reviewMapper, id);
    }


}
