package edu.ted.web.movieland;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "edu.ted.web.movieland.service",
        "edu.ted.web.movieland.utils",
        "edu.ted.web.movieland.dao"})
@PropertySource("classpath:queries.properties")
public class MovieLandJavaConfiguration {

    @Bean
    public PropertyOverrideConfigurer getDataSourceConfigurator() {
        PropertyOverrideConfigurer overrideConfigurator = new PropertyOverrideConfigurer();
        overrideConfigurator.setLocation(new ClassPathResource("dataSource.properties"));
        return overrideConfigurator;
    }

    @Bean("dataSourceConfig")
    public HikariConfig getHikariConfig() {
        return new HikariConfig();
    }

    @Bean("dataSource")
    public DataSource getDataSource(HikariConfig config) {
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
