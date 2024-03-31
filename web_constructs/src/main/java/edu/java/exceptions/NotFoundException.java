package edu.java.exceptions;

public class NotFoundException extends Exception {

    public final String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
