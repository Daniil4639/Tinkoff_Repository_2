package edu.java.api_exceptions;

import org.springframework.web.reactive.function.client.ClientResponse;

public class ApiResponseErrorException extends Exception {

    public final ClientResponse response;

    public ApiResponseErrorException(ClientResponse response) {
        this.response = response;
    }
}
