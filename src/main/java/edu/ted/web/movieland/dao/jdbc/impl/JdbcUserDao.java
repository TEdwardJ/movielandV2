package edu.ted.web.movieland.dao.jdbc.impl;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.dao.jdbc.mapper.RoleRowMapper;
import edu.ted.web.movieland.dao.jdbc.mapper.UserRowMapper;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private final String findUserQuery;
    private final String checkPasswordQuery;
    private final String findUserRolesQuery;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = new UserRowMapper();
    private RowMapper<UserRole> roleRowMapper = new RoleRowMapper();

    public JdbcUserDao(JdbcTemplate jdbcTemplate,
                       @Value("${findUserByEmailQuery}") String findUserQuery,
                       @Value("${checkPasswordQuery}") String checkPasswordQuery,
                       @Value("${findUserRolesQuery}") String findUserRolesQuery) {
        this.findUserQuery = findUserQuery;
        this.checkPasswordQuery = checkPasswordQuery;
        this.jdbcTemplate = jdbcTemplate;
        this.findUserRolesQuery = findUserRolesQuery;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return jdbcTemplate.query(findUserQuery, userMapper, email)
                .stream()
                .findFirst();
    }

    @Override
    public User enrichUserWithRoles(User user) {
        List<UserRole> roles = jdbcTemplate.query(findUserRolesQuery, roleRowMapper, user.getId());//.queryForList(findUserRolesQuery, UserRole.class);
        user.setRoles(roles);
        return user;
    }

    @Override
    public boolean isPasswordValid(String email, String encryptedPassword) {
        return Integer.valueOf(1).equals(jdbcTemplate.queryForObject(checkPasswordQuery, Integer.class, email, encryptedPassword));
    }

}
