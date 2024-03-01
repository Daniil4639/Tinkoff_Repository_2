package edu.java.clients;

import edu.java.response.resource.GitHubResponse;
import org.springframework.http.MediaType;

public class GitHubClient extends Client {

    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public GitHubResponse getInfo(String user, String repos) {
        return client.get()
            .uri("/repos/{user}/{repos}", user, repos)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .log()
            .block();
    }
}
