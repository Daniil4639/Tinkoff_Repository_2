package edu.java.configuration;

import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public BotClient getBotClient(ApplicationConfig config) {
        return new BotClient(config.api().botBaseUrl());
    }

    @Bean
    public GitHubClient getGitHubClient(ApplicationConfig config) {
        return new GitHubClient(config.api().gitHubBaseUrl());
    }

    @Bean
    public StackOverflowClient getStackOverflowClient(ApplicationConfig config) {
        return new StackOverflowClient(config.api().stackOverFlowBaseUrl());
    }
}
