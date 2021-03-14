package edu.ted.web.movieland;

import edu.ted.web.movieland.service.impl.DefaultSecurityService;
import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@Slf4j
public class TestDbConfiguration {

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource, String testUserPassword, String testUserEmail) {

        String sole = GeneralUtils.generateString(10);
        var encryptedPassword = DefaultSecurityService.getEncrypted(testUserPassword + sole);

        Flyway flyway = Flyway.configure(this.getClass().getClassLoader())
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
        return GeneralUtils.generateString(15);//"testUserPassword";
    }

    @Bean("testUserEmail")
    public String testUserEmail() {
        return "test_user@gmail.com";
    }

}
