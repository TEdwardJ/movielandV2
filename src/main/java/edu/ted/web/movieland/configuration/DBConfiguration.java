package edu.ted.web.movieland.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource("classpath:queries.properties")
public class DBConfiguration {

    @Value("${hibernate.connection.charSet}")
    private String charSet;

    @Value("${hibernate.connection.charSetEncoding}")
    private String charSetEncoding;

    @Value("${hibernate.dialect}")
    private String sqlDialect;

    @Value("${hbm2ddl.auto}")
    private String hbm2dllAuto;

    @Value("${hibernate.show_sql}")
    private String showSQL;// = true;
    @Value("${hibernate.format_sql}")
    private String formatSQL;

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

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        Properties jpaProp = new Properties();
        jpaProp.put("hibernate.hbm2ddl.auto", hbm2dllAuto);
        jpaProp.put("show_sql", showSQL);
        jpaProp.put("format_sql", formatSQL);
        jpaProp.put("hibernate.dialect", sqlDialect);
        jpaProp.put("hibernate.connection.charSet", charSet);
        jpaProp.put("hibernate.connection.useUnicode", true);
        jpaProp.put("hibernate.connection.charSetEncoding", charSetEncoding);
        emf.setPackagesToScan("edu.ted.web.movieland.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

}
