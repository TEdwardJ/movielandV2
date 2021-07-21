package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<UserRole> {

    @Override
    public UserRole mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var role = UserRole.valueOf(resultSet.getString("ROLE_NAME"));
        return role;
    }
}
