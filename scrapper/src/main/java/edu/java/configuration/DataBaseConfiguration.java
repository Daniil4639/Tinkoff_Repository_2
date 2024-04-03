package edu.java.configuration;

import javax.sql.DataSource;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    private String dataBaseUrl;

    @Value("${spring.datasource.username}")
    private String dataBaseUsername;

    @Value("${spring.datasource.password}")
    private String dataBasePassword;

    @Bean
    public DataSource getDataBase() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataBaseUrl);
        dataSource.setUsername(dataBaseUsername);
        dataSource.setPassword(dataBasePassword);

        return dataSource;
    }

    @Bean
    public DefaultConfigurationCustomizer configurationCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderQuotedNames(
                RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
            );
    }
}
