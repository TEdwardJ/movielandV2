package edu.ted.web.movieland.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:queries.properties")
public class DBConfiguration {

    @Bean
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(@Value("${dataSource.url}") String url,
                                 @Value("${dataSource.dataSourceClassName:}") String dataSourceClassName,
                                 @Value("${dataSource.username}") String userName,
                                 @Value("${dataSource.password}") String password,
                                 HikariConfig hikariConfig) {

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

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource,
                                                                          @Value("${hibernate.connection.charSet}")
                                                                                  String charSet,
                                                                          @Value("${hibernate.connection.charSetEncoding}")
                                                                                  String charSetEncoding,
                                                                          @Value("${hibernate.dialect}")
                                                                                  String sqlDialect,
                                                                          @Value("${hibernate.show_sql}")
                                                                                  Object showSql,
                                                                          @Value("${hibernate.format_sql}")
                                                                                  Object formatSql,
                                                                          @Value("${hibernate.default_schema}")
                                                                                  Object defaultSchema) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("edu.ted.web.movieland.entity");
        Properties jpaProp = new Properties();
        jpaProp.put("hibernate.dialect", sqlDialect);
        jpaProp.put("hibernate.default_schema", defaultSchema);
        jpaProp.put("hibernate.show_sql", showSql);
        jpaProp.put("hibernate.format_sql", formatSql);
        jpaProp.put("hibernate.connection.charSet", charSet);
        jpaProp.put("hibernate.connection.useUnicode", true);
        jpaProp.put("hibernate.connection.charSetEncoding", charSetEncoding);
        entityManagerFactory.setJpaProperties(jpaProp);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactory;
    }

}
