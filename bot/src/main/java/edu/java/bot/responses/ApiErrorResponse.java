package edu.java.bot.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {

    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private String[] stacktrace;


}
