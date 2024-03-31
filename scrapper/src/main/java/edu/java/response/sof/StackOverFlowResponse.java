package edu.java.response.sof;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StackOverFlowResponse {
    @JsonProperty("title")
    private String questionName;
    @JsonProperty("link")
    private String questionLink;
    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;
    @JsonProperty("last_activity_date")
    private OffsetDateTime lastUpdate;
}
