package edu.java.response;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("MemberName")
public class StackOverFlowResponse {
    private String title;
    private String link;
    private OffsetDateTime creation_date;
    private OffsetDateTime last_edit_date;

    public ResourceResponse getResponse() {
        return new ResourceResponse(title, link, creation_date, last_edit_date);
    }
}
