package edu.java.configuration;

import javax.sql.DataSource;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfiguration {

    private static final String USER_PASSWORD_STRING = "postgres";
    private static final String DATA_BASE_URL = "jdbc:postgresql://localhost:5432/scrapper";

    @Bean
    public DataSource getDataBase() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(DATA_BASE_URL);
        dataSource.setUsername(USER_PASSWORD_STRING);
        dataSource.setPassword(USER_PASSWORD_STRING);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDataBase());
    }

    @Bean
    public DefaultConfigurationCustomizer configurationCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderQuotedNames(
                RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
            );
    }
}
