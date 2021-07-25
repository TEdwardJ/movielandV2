package edu.ted.web.movieland.dao.jdbc.mapper;

import edu.ted.web.movieland.entity.UserRoleName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<UserRoleName> {

    @Override
    public UserRoleName mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        var role = UserRoleName.valueOf(resultSet.getString("ROLE_NAME"));
        return role;
    }
}
