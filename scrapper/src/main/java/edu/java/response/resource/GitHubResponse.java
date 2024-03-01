package edu.java.response.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GitHubResponse {
    @JsonProperty("name")
    private String reposName;
    @JsonProperty("html_url")
    private String reposLink;
    @JsonProperty("created_at")
    private OffsetDateTime creationDate;
    @JsonProperty("updated_at")
    private OffsetDateTime lastUpdate;
}
