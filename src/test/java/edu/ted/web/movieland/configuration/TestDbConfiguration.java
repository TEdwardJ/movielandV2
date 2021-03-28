package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Flyway flyway(DataSource dataSource) {
        var sole = GeneralUtils.generateString(10);
        var encryptedPassword = GeneralUtils.getEncrypted(testUserPassword() + sole);

        var flyway = Flyway.configure(this.getClass().getClassLoader())
                .baselineOnMigrate(false)
                .baselineVersion("0.0.0")
                .placeholderReplacement(true)
                .dataSource(dataSource)
                .placeholders(Map.of("email", testUserEmail, "sole", sole, "password", encryptedPassword))
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
    public String testUserPassword() {
        return GeneralUtils.generateString(15);
    }
}
