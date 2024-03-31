package edu.java.response.sof;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackOverFlowResponseItems {

    @JsonProperty("items")
    private List<StackOverFlowResponse> responseList;
}
