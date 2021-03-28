package edu.ted.web.movieland.dao.jdbc.impl;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.dao.jdbc.mapper.UserRowMapper;
import edu.ted.web.movieland.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private final String findUserQuery;
    private final String checkPasswordQuery;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = new UserRowMapper();

    public JdbcUserDao(DataSource dataSource,
                       JdbcTemplate jdbcTemplate,
                       @Value("${findUserByEmailQuery}") String findUserQuery,
                       @Value("${checkPasswordQuery}") String checkPasswordQuery) {
        this.findUserQuery = findUserQuery;
        this.checkPasswordQuery = checkPasswordQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return jdbcTemplate.query(findUserQuery, userMapper, email)
                .stream()
                .findFirst();
    }

    @Override
    public boolean isPasswordValid(String email, String encryptedPassword) {
        return jdbcTemplate.queryForObject(checkPasswordQuery, Integer.class, email, encryptedPassword) == 1;
    }

}
