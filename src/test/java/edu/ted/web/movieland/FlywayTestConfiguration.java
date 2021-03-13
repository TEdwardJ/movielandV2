package edu.ted.web.movieland;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.service.UserService;
import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class FlywayTestConfiguration {

    @Autowired
    private UserService userService;

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource){
        Flyway flyway = Flyway.configure(this.getClass().getClassLoader())
                .baselineOnMigrate(false)
                .baselineVersion("0.0.0")
                .cleanDisabled(true)
                .dataSource(dataSource)
                .schemas("movie")
                .locations("filesystem:db/migration")
                .load();

        return flyway;
    }

    @Bean("testUserPassword")
    public String testUserPassword(){
        return GeneralUtils.generateString(15);
    }

    @Bean("testUserEmail")
    public String testUserEmail(){
        return "test_user@gmail.com";
    }

    @Bean
    @DependsOn("flyway")
    public User createUser(String testUserPassword, String testUserEmail){
        User testUser = new User();
        testUser.setEmail(testUserEmail);
        testUser.setNickname("testUser");
        testUser.setPassword(testUserPassword);
        userService.addUser(testUser);
        return testUser;
    }

}
