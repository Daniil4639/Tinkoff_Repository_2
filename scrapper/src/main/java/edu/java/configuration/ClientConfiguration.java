package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient getGitHubClient(ApplicationConfig config) {
        return new GitHubClient(config.gitHubBaseUrl());
    }

    @Bean
    public StackOverflowClient getStackOverflowClient(ApplicationConfig config) {
        return new StackOverflowClient(config.stackOverFlowBaseUrl());
    }
}
