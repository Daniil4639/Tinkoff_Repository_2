package edu.java.bot.api_exceptions;

import org.springframework.web.reactive.function.client.ClientResponse;

public class BadRequestException extends Exception {

    public final ClientResponse response;

    public BadRequestException(ClientResponse response) {
        this.response = response;
    }
}
