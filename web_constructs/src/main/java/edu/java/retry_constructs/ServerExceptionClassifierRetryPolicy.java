package edu.java.retry_constructs;

import java.util.List;
import org.springframework.classify.Classifier;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpServerErrorException;

public class ServerExceptionClassifierRetryPolicy extends ExceptionClassifierRetryPolicy {

    public ServerExceptionClassifierRetryPolicy(Integer attempts, List<Integer> codes) {
        final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(attempts);

        this.setExceptionClassifier(new Classifier<Throwable, RetryPolicy>() {
            @Override
            public RetryPolicy classify(Throwable classifiable) {
                if (classifiable instanceof HttpServerErrorException) {
                    if (codes
                        .contains(((HttpServerErrorException) classifiable).getStatusCode().value())) {

                        return simpleRetryPolicy;
                    }
                    return new NeverRetryPolicy();
                }
                return new NeverRetryPolicy();
            }
        });
    }
}
