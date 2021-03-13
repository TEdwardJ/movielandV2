 package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.dao.jdbc.mapper.UserRowMapper;
import edu.ted.web.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcUserDao implements UserDao {

    private final String findUserQuery;
    private final String checkPasswordQuery;

    private final SimpleJdbcInsert userInsert;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = new UserRowMapper();

    public JdbcUserDao(DataSource dataSource,
                       JdbcTemplate jdbcTemplate,
                       @Value("${findUserByEmailQuery}") String findUserQuery,
                       @Value("${checkPasswordQuery}") String checkPasswordQuery){
        this.findUserQuery = findUserQuery;
        this.checkPasswordQuery = checkPasswordQuery;
        this.jdbcTemplate = jdbcTemplate;
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName("movie")
                .withTableName("user")
                .usingGeneratedKeyColumns("usr_id");
    }

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(findUserQuery, userMapper, email);
    }

    @Override
    public boolean isPasswordValid(String email, String encryptedPassword) {
        return jdbcTemplate.queryForObject(checkPasswordQuery, Integer.class, email, encryptedPassword) == 1;
    }

    @Override
    public int addUser(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USR_NAME", user.getNickname());
        parameters.put("USR_EMAIL", user.getEmail());
        parameters.put("USR_PASSWORD_ENC", user.getPassword());
        parameters.put("USR_SOLE", user.getSole());
        var key = userInsert.executeAndReturnKey(parameters);
        return key.intValue();
    }
}
