package edu.java.bot.configuration.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@EnableKafka
public class KafkaTopicConfiguration {

    @Autowired
    private ApplicationConfig config;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
            config.kafka().bootstrapServer()
        );

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic messagesTopic() {
        return new NewTopic(
            config.kafka().topicName() + "_dlq",
            config.kafka().partitionsCount(),
            config.kafka().replicationCount()
        );
    }
}
