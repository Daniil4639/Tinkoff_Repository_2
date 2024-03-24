package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfiguration {

    private static final String USER_PASSWORD_STRING = "postgres";

    @Bean
    public DataSource getDataBase() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        dataSource.setUsername(USER_PASSWORD_STRING);
        dataSource.setPassword(USER_PASSWORD_STRING);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDataBase());
    }
}
