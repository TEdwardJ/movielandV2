package edu.ted.web.movieland.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {
    @Value("${dataSource.Url}")
    private String url;
    @Value("${dataSource.dataSourceClassName}")
    private String driverClassName;
    @Value("${dataSource.username}")
    private String userName;
    @Value("${dataSource.password}")
    private String password;

    @Bean("dataSource")
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }
}
