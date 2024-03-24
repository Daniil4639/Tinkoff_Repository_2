package edu.java.exceptions;

public class BadRequestException extends Exception {

    public final String message;

    public BadRequestException(String message) {
        this.message = message;
    }
}
