package edu.java.configuration;

import edu.java.configuration.retryWebClient.RetryType;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    AccessType databaseAccessType,

    @NotNull
    RetryType retryType,

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
}
