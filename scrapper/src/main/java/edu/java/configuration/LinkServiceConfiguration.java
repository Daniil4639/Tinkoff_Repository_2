package edu.java.configuration;

import edu.java.db_services.LinksService;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.domain.jooq.JooqLinkDao;
import edu.java.domain.jpa.JpaLinkDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkServiceConfiguration {

    @Autowired
    private JdbcLinkDao jdbcLinkDao;
    @Autowired
    private JooqLinkDao jooqLinkDao;
    @Autowired
    private JpaLinkDao jpaLinkDao;

    @Bean
    public LinksService getLinkDao(ApplicationConfig config, GitHubService gitHubService,
        StackOverFlowService stackOverFlowService) {

        switch (config.databaseAccessType()) {
            case JDBC -> {
                return new LinksService(gitHubService, stackOverFlowService, jdbcLinkDao);
            }
            case JOOQ -> {
                return new LinksService(gitHubService, stackOverFlowService, jooqLinkDao);
            }
            case JPA -> {
                return new LinksService(gitHubService, stackOverFlowService, jpaLinkDao);
            }
            default -> {
                return null;
            }
        }
    }
}
