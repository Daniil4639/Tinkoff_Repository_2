package edu.java.controller;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.response.ResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
public class LinkParser {

    public final GitHubClient gitHubClient;

    public final StackOverflowClient stackOverflowClient;

    public LinkParser(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    @GetMapping("https://github.com/{userName}/{repositoryName}")
    public ResourceResponse getGitHubResource(@PathVariable String userName, @PathVariable String repositoryName) {

        return gitHubClient.getGitHubInfo("repos/" + userName + "/" + repositoryName).getResponse();
    }

    @GetMapping("https://stackoverflow.com/questions/{id}/{name}")
    public ResourceResponse getStackOverFlowResource(@PathVariable String id) {

        return stackOverflowClient.getStackOverFlowInfo(id + "?site=stackoverflow").getResponse();
    }
}
