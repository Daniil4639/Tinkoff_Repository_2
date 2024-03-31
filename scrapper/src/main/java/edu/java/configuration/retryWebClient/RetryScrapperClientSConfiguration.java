package edu.java.configuration.retryWebClient;

import edu.java.configuration.ApplicationConfig;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
public class RetryScrapperClientSConfiguration {

    private static final int MAX_ATTEMPTS_COUNT = 3;
    private static final int SECOND_BACKOFF = 2;

    @Bean
    public Retry getRetry(ApplicationConfig config) {
        switch (config.retryType()) {
            case CONSTANT -> {
                return Retry.fixedDelay(MAX_ATTEMPTS_COUNT, Duration.ofSeconds(SECOND_BACKOFF))
                    .filter(this::is5xxServerError);
            }
            case EXPONENTIAL -> {
                return Retry.backoff(MAX_ATTEMPTS_COUNT, Duration.ofSeconds(SECOND_BACKOFF))
                    .filter(this::is5xxServerError);
            }
            default -> {
                return null;
            }
        }
    }

    private boolean is5xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException
            && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }
}
