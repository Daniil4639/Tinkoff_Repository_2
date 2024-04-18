package edu.java.retry_constructs;

import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

@Setter
public class LinearBackoffPolicy implements BackOffPolicy {

    private static final Long DEFAULT_INTERVAL_VALUE = 100L;
    private static final Long DEFAULT_MAX_INTERVAL_VALUE = 30000L;

    private Long interval = DEFAULT_INTERVAL_VALUE;
    private Long maxInterval = DEFAULT_MAX_INTERVAL_VALUE;

    @Override
    public BackOffContext start(RetryContext context) {
        return new LinearBackoffContext();
    }

    @SneakyThrows
    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        LinearBackoffContext linearBackoffContext = (LinearBackoffContext) backOffContext;

        long sleepDuration = Math.min(maxInterval, interval * linearBackoffContext.attemptNumber);
        linearBackoffContext.attemptNumber++;

        Thread.sleep(sleepDuration);
    }

    private static class LinearBackoffContext implements BackOffContext {
        private Integer attemptNumber = 0;
    }
}
