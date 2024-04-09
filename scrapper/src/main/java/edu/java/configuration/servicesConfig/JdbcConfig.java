package edu.java.configuration.servicesConfig;

import edu.java.db_services.ChatService;
import edu.java.db_services.LinksService;
import edu.java.domain.jdbc.JdbcChatRepository;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcConfig {

    @Bean
    public LinksService jdbcLinksService(GitHubService gitHubService,
        StackOverFlowService stackOverFlowService, JdbcLinkDao linkDao) {
        return new LinksService(gitHubService, stackOverFlowService, linkDao);
    }

    @Bean
    public ChatService jdbcChatService(JdbcChatRepository repository) {
        return new ChatService(repository);
    }
}
