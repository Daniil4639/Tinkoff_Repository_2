package edu.java.bot.configuration;

import edu.java.bot.configuration.retryWebClient.RetryType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    RetryType retryType,

    @NotEmpty
    String telegramToken,

    @NotNull
    String scrapperBaseUrl
) {
}
