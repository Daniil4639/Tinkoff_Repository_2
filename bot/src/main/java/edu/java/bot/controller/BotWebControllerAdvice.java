package edu.java.bot.controller;

import edu.java.bot.api_exceptions.IncorrectUpdateRequest;
import edu.java.bot.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BotWebControllerAdvice {

    @ExceptionHandler(IncorrectUpdateRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleUpdateError(Exception exception) {
        return new ApiErrorResponse("", "400", "IncorrectUpdateRequest", exception.getMessage(), null);
    }
}
