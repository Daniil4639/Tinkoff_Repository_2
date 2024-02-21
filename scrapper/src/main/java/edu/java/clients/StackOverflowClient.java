package edu.java.clients;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import java.util.Objects;

public class StackOverflowClient extends Client{

    public StackOverflowClient(String url) {
        super(Objects.requireNonNullElse(url, "https://stackoverflow.com/"));
    }
}
