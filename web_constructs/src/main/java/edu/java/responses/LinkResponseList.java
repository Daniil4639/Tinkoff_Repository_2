package edu.java.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkResponseList {

    private LinkResponse[] links;
    private Integer size;
}
