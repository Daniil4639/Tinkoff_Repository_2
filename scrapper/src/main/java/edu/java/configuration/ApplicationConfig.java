package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    boolean useQueue,

    @NotNull
    KafkaConfig kafka,

    @NotNull
    ApiConfig api,

    @NotNull
    Scheduler scheduler
) {
    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay) {}

    public record ApiConfig(@NotNull String gitHubBaseUrl,
                            @NotNull String stackOverFlowBaseUrl,
                            @NotNull String botBaseUrl) {}

    public record KafkaConfig(@NotNull String bootstrapServer,
                              @NotNull String topicName,
                              @NotNull int partitionsCount,
                              @NotNull short replicationCount) {}
}
