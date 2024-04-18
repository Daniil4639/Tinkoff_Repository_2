package edu.java.bot.configuration;

import edu.java.retry_constructs.RetryInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,

    @NotNull
    String scrapperBaseUrl,

    @NotNull
    KafkaConfig kafka,

    @NotNull
    RetryInfo retryInfo
) {
    public record KafkaConfig(@NotNull String bootstrapServer,
                              @NotNull String topicName,
                              @NotNull String groupId,
                              @NotNull int partitionsCount,
                              @NotNull short replicationCount) {}
}
