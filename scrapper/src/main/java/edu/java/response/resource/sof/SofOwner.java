package edu.java.response.resource.sof;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SofOwner {

    @JsonProperty("display_name")
    private String name;

    @JsonProperty("reputation")
    private int rep;
}
