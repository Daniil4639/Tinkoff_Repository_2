package edu.java.response.resource.sof;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StackOverFlowAnswer {

    private SofOwner owner;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;
}
