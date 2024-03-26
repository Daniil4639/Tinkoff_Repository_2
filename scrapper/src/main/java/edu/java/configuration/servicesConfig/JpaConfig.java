package edu.java.configuration.servicesConfig;

import edu.java.db_services.ChatService;
import edu.java.db_services.LinksService;
import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaConfig {

    @Bean
    public LinksService jpaLinksService(
        GitHubService gitHubService,
        StackOverFlowService stackOverFlowService, JpaLinkDao linkDao) {
        return new LinksService(gitHubService, stackOverFlowService, linkDao);
    }

    @Bean
    public ChatService jpaChatService(JpaChatRepository repository) {
        return new ChatService(repository);
    }
}
