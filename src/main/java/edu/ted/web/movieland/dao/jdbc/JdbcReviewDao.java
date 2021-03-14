package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.ReviewDao;
import edu.ted.web.movieland.dao.jdbc.mapper.ReviewRowMapper;
import edu.ted.web.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReviewDao implements ReviewDao {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert reviewInsert;
    private String movieReviewsQuery;
    private RowMapper<Review> reviewMapper = new ReviewRowMapper();

    public JdbcReviewDao(DataSource dataSource, JdbcTemplate jdbcTemplate, @Value("${getMovieReviewsQuery}") String movieReviewsQuery) {
        this.reviewInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName("movie")
                .withTableName("movie_Review")
                .usingGeneratedKeyColumns("review_id");

        this.jdbcTemplate = jdbcTemplate;
        this.movieReviewsQuery = movieReviewsQuery;
    }

    @Override
    public int addReview(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USR_ID", review.getUser().getId());
        parameters.put("MESSAGE", review.getText());
        parameters.put("M_ID", review.getMovieId());
        parameters.put("REVIEW_DATE", review.getReviewDate());
        var key = reviewInsert.executeAndReturnKey(parameters);
        return key.intValue();
    }

    @Override
    public List<Review> findAllByMovieId(int movieId) {
        return jdbcTemplate.query(movieReviewsQuery, reviewMapper, movieId);
    }
}
