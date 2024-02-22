package edu.java.clients;

import edu.java.response.GitHubResponse;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class GitHubClient extends Client {

    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public GitHubResponse getGitHubInfo(String url) {
        Mono<GitHubResponse> response = client.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubResponse.class).log();

        return response.block();
    }
}
