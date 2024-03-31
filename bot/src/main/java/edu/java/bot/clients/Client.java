package edu.java.bot.clients;

import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class Client {

    protected final WebClient client;

    public Client(String url) {
        client = WebClient.builder()
            .filter(ExchangeFilterFunctions.basicAuthentication("UpdateRunnerBot",
                "updateRunnerBotPassword"))
            .baseUrl(url)
            .build();
    }
}
