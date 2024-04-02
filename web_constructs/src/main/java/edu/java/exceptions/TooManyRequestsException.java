package edu.java.exceptions;

public class TooManyRequestsException extends Exception {

    public TooManyRequestsException() {
        super("Превышен лимит запросов. Попробуйте позже!");
    }
}
