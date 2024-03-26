package edu.java.configuration.servicesConfig;

import edu.java.db_services.ChatService;
import edu.java.db_services.LinksService;
import edu.java.domain.jooq.JooqChatRepository;
import edu.java.domain.jooq.JooqLinkDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqConfig {

    @Bean
    public LinksService jooqLinksService(GitHubService gitHubService,
        StackOverFlowService stackOverFlowService, JooqLinkDao linkDao) {
        return new LinksService(gitHubService, stackOverFlowService, linkDao);
    }

    @Bean
    public ChatService jooqChatService(JooqChatRepository repository) {
        return new ChatService(repository);
    }
}
