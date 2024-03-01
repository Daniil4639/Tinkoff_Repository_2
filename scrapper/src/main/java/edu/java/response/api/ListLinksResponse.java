package edu.java.response.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListLinksResponse {

    private LinkResponse[] links;
    private Integer size;
}
