package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var user = new User();
        user.setId(resultSet.getInt("USR_ID"));
        user.setNickname(resultSet.getString("USR_NAME"));
        user.setEmail(resultSet.getString("USR_EMAIL"));
        user.setSole(resultSet.getString("USR_SOLE"));
        return user;
    }
}
