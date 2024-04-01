package edu.java.configuration;

import edu.java.retry_constructs.LinearBackoffPolicy;
import edu.java.retry_constructs.RetryType;
import edu.java.retry_constructs.ServerExceptionClassifierRetryPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Slf4j
public class RetryConfiguration {

    @Bean
    public RetryTemplate retryTemplate(ApplicationConfig config) {
        RetryTemplate template = new RetryTemplate();

        template.setRetryPolicy(new ServerExceptionClassifierRetryPolicy(
            config.retryInfo().attempts(), config.retryInfo().codes()));

        switch (config.retryInfo().type()) {
            case RetryType.EXPONENTIAL -> {
                setExponentialBackoff(template, config.retryInfo().interval());
            }
            case RetryType.CONSTANT -> {
                setConstantBackoff(template, config.retryInfo().interval());
            }
            case RetryType.LINEAR -> {
                setLinearBackoff(template, config.retryInfo().interval());
            }
            default -> {
                log.error("RetryType не распознан!");
            }
        }

        return template;
    }

    private void setExponentialBackoff(RetryTemplate template, Integer interval) {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(interval);
        backOffPolicy.setMultiplier(2);

        template.setBackOffPolicy(backOffPolicy);
    }

    private void setConstantBackoff(RetryTemplate template, Integer interval) {
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(interval);

        template.setBackOffPolicy(backOffPolicy);
    }

    private void setLinearBackoff(RetryTemplate template, Integer interval) {
        LinearBackoffPolicy backoffPolicy = new LinearBackoffPolicy();
        backoffPolicy.setInterval(Long.valueOf(interval));

        template.setBackOffPolicy(backoffPolicy);
    }
}
