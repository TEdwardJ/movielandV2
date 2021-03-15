package edu.ted.web.movieland.configuration;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.dao.cache.CaffeineCachedGenreDao;
import edu.ted.web.movieland.dao.cache.CustomCachedGenreDao;
import edu.ted.web.movieland.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;
import java.util.Map;

@Configuration("testDbConfiguration")
@Slf4j
public class TestDbConfiguration {
    @Autowired
    DataSource dataSource;

    @Value("${testUser.email}")
    private String testUserEmail;

    @Bean(autowireCandidate = false)
    public Flyway flyway() {
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
        flyway.migrate();
        return flyway;
    }

    @Bean("testUserPassword")
    public String testUserPassword() {
        return GeneralUtils.generateString(15);
    }


    @Bean(value = "testCustomCachedGenreDao")
    @Lazy
    public static GenreDao customGenreDao(GenreDao jdbcGenreDao, GenreDao cachedDao){
        if (cachedDao instanceof CustomCachedGenreDao) {
            return cachedDao;
        }
        return new CustomCachedGenreDao(jdbcGenreDao);
    }

    @Bean("testCaffeineCachedGenreDao")
    @Lazy
    public static GenreDao caffeineGenreDao(GenreDao jdbcGenreDao, GenreDao cachedDao){
        if (cachedDao instanceof CaffeineCachedGenreDao) {
            return cachedDao;
        }
        return new CaffeineCachedGenreDao(jdbcGenreDao);
    }

}
