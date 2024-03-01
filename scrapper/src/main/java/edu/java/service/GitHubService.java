package edu.java.service;

import edu.java.clients.GitHubClient;
import edu.java.response.resource.GitHubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final GitHubClient gitHubClient;

    public GitHubResponse getGitHubInfo(String userName, String repositoryName) {
        return gitHubClient.getInfo(userName, repositoryName);
    }
}
