package edu.ted.web.movieland.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:queries.properties")
public class DBConfiguration {

    @Bean
    public DataSource dataSource(@Value("${dataSource.url}") String url,
                                 @Value("${dataSource.dataSourceClassName:}") String dataSourceClassName,
                                 @Value("${dataSource.username}") String userName,
                                 @Value("${dataSource.password}") String password) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDataSourceClassName(dataSourceClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
