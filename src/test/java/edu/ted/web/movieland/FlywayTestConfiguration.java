package edu.ted.web.movieland;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class FlywayTestConfiguration {

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

}
