package edu.java.clients;

import org.springframework.web.reactive.function.client.WebClient;

public class Client {

    protected final WebClient client;

    public Client(String url) {
        client = WebClient.builder().baseUrl(url).build();
    }
}
