package edu.java.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ApiErrorResponse {

    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private String[] stacktrace;
}
