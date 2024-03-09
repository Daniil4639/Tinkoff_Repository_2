package edu.java.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BotApiError {

    private String exceptionMessage;
}
