package edu.ted.web.movieland.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Autowired
    private Environment env;
    @Value("${hikaricp.configurationFile}")
    private String dsConfigFile;
    @Value("${dataSource.Url}")
    private String url;
    @Value("${dataSource.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${dataSource.username}")
    private String userName;
    @Value("${dataSource.password}")
    private String password;

    @Bean("dataSource")
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig(dsConfigFile);

/*        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDataSourceClassName(dataSourceClassName);*/
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }
}
