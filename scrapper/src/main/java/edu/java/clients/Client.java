package edu.java.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class Client {

    @Autowired
    protected RetryTemplate retryTemplate;

    protected final WebClient client;

    public Client(String url) {
        client = WebClient.builder()
            .filter(ExchangeFilterFunctions.basicAuthentication("UpdateRunnerBot",
                "updateRunnerBotPassword"))
            .baseUrl(url)
            .build();
    }
}
