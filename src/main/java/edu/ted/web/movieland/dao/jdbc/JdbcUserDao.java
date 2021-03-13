 package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.dao.jdbc.mapper.UserRowMapper;
import edu.ted.web.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {

    private final String findUserQuery;
    private final String checkPasswordQuery;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new UserRowMapper();

    public JdbcUserDao(@Value("${findUserByEmailQuery}") String findUserQuery,
                       @Value("${checkPasswordQuery}") String checkPasswordQuery){
        this.findUserQuery = findUserQuery;
        this.checkPasswordQuery = checkPasswordQuery;
    }

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(findUserQuery, userMapper, email);
    }

    @Override
    public boolean isPasswordValid(String email, String encryptedPassword) {
        return jdbcTemplate.queryForObject(checkPasswordQuery, Integer.class, new Object[]{email, encryptedPassword}) == 1;
    }
}
