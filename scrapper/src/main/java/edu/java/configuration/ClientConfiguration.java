package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.clients.bot.BotClient;
import edu.java.clients.bot.BotHttpClient;
import edu.java.clients.bot.BotKafkaClient;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ClientConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "false")
    public BotClient getBotHttpClient(ApplicationConfig config) {
        return new BotHttpClient(config.api().botBaseUrl());
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "true")
    public BotClient getBotKafkaClient(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate,
        ApplicationConfig config) {

        return new BotKafkaClient(kafkaTemplate, config.kafka().topicName());
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
