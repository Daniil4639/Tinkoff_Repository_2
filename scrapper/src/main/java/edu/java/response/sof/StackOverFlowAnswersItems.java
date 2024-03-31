package edu.java.response.sof;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StackOverFlowAnswersItems {

    @JsonProperty("items")
    private List<StackOverFlowAnswer> answers;
}
