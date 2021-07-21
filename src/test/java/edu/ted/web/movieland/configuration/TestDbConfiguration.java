package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
public class TestDbConfiguration {

    private final String testUserEmail;

    public TestDbConfiguration(@Value("${testUser.email}") String testUserEmail) {
        this.testUserEmail = testUserEmail;
    }

    @Bean(autowireCandidate = false, initMethod = "migrate")
    @Qualifier("flywayTest")
    public Flyway flyway(DataSource dataSource, PasswordEncoder encoder) {
        var salt = GeneralUtils.generateStringWithLettersAndNumbers(10);
        var encryptedPassword = GeneralUtils.getEncrypted(testUserPassword(encoder) + salt);

        var flyway = Flyway.configure(this.getClass().getClassLoader())
                .baselineOnMigrate(false)
                .baselineVersion("0.0.0")
                .placeholderReplacement(true)
                .dataSource(dataSource)
                .placeholders(Map.of("email", testUserEmail, "salt", salt, "password", encryptedPassword, "role", "2"))
                .schemas("movie")
                .locations("filesystem:db/migration", "classpath:db/migration")
                .load();
        if (flyway.info().applied().length > 0) {
            if (!flyway.info().current().getVersion().toString().equals("0.0.0")) {
                log.debug("Flyway`s last applied version {}", flyway.info().current().getVersion().toString());
                flyway.clean();
            }
        }
        return flyway;
    }

    @Bean("testUserPassword")
    public String testUserPassword(PasswordEncoder encoder) {
        GeneralUtils.setPasswordEncoder(encoder);
        return GeneralUtils.generateStringWithLettersAndNumbers(15);
    }
}
