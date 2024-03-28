package edu.java.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

public abstract class Client {

    protected final WebClient client;
    @Autowired
    protected Retry retry;

    public Client(String url) {
        client = WebClient.builder()
            .filter(ExchangeFilterFunctions.basicAuthentication("UpdateRunnerBot",
                "updateRunnerBotPassword"))
            .baseUrl(url)
            .build();
    }
}
