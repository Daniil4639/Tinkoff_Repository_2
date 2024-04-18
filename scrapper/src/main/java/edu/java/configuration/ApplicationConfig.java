package edu.java.configuration;

import edu.java.retry_constructs.RetryInfo;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableRetry
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    boolean useQueue,

    @NotNull
    KafkaConfig kafka,

    @NotNull
    AccessType databaseAccessType,

    @NotNull
    ApiConfig api,

    @NotNull
    RetryInfo retryInfo,

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
