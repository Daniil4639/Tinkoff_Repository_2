package edu.java.bot.api_exceptions;

import org.springframework.web.reactive.function.client.ClientResponse;

public class NotFoundException extends Exception {

    public final ClientResponse response;

    public NotFoundException(ClientResponse response) {
        this.response = response;
    }
}
