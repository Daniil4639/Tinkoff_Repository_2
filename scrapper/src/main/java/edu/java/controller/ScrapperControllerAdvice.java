package edu.java.controller;

import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.response.api.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperControllerAdvice {

    @ExceptionHandler({IncorrectChatOperationRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleChatError(Exception exception) {
        return new ApiErrorResponse("", "400", "IncorrectChatOperationRequest", exception.getMessage(), null);
    }

    @ExceptionHandler(DoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleExistError(Exception exception) {
        return new ApiErrorResponse("", "404", "DoesNotExistException", exception.getMessage(), null);
    }
}
