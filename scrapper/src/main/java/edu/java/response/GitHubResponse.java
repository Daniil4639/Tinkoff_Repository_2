package edu.java.response;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("MemberName")
public class GitHubResponse {
    private String name;
    private String html_url;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public ResourceResponse getResponse() {
        return new ResourceResponse(name, html_url, created_at, updated_at);
    }
}
