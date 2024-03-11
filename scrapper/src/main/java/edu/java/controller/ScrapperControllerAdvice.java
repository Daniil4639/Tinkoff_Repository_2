package edu.java.controller;

import edu.java.exceptions.DoesNotExistException;
import edu.java.exceptions.IncorrectRequest;
import edu.java.responses.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperControllerAdvice {

    @ExceptionHandler({IncorrectRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleChatError(Exception exception) {
        return getApiErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler(DoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleExistError(Exception exception) {
        return getApiErrorResponse(exception, exception.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    private ApiErrorResponse getApiErrorResponse(Throwable exception, String description,
        String code) {

        return ApiErrorResponse.builder()
            .description(description)
            .code(code)
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(getStacktrace(exception))
            .build();
    }

    private String[] getStacktrace(Throwable exception) {
        return Arrays.stream(exception.getStackTrace())
            .map(StackTraceElement::toString)
            .toArray(String[]::new);
    }
}
