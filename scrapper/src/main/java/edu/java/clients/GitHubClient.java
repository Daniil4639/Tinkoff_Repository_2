package edu.java.clients;

import edu.java.dto.github.BranchDto;
import edu.java.dto.github.CommitDto;
import edu.java.dto.github.CommitExtendedDto;
import edu.java.response.github.GitHubExtendedResponse;
import edu.java.response.github.GitHubResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

public class GitHubClient extends Client {

    private final ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public GitHubResponse getInfo(String user, String repos) {
        Optional<GitHubResponse> response = client.get()
            .uri("/repos/{user}/{repos}", user, repos)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .log()
            .blockOptional();

        response.ifPresent(value -> value.setLastUpdate(value.getLastUpdate().toLocalDateTime().atOffset(offset)));
        return response.orElse(null);
    }

    public GitHubExtendedResponse getExtendedInfo(String user, String repos,
        OffsetDateTime lastUpdate) {

        GitHubExtendedResponse response = new GitHubExtendedResponse();

        List<BranchDto> branches = getBranches(user, repos);

        for (BranchDto branch: branches) {
            List<CommitDto> commits = getCommitsByBranch(
                String.format("/repos/%s/%s/commits?sha=%s", user, repos, branch.getCommit().getSha()),
                lastUpdate);

            if (commits == null || commits.isEmpty()) {
                continue;
            }

            response.getBranches().add(branch.getName());
            response.getCommitsByBranches().add(commits);
        }

        return response;
    }

    private List<BranchDto> getBranches(String user, String repos) {
        return client.get()
            .uri("/repos/{user}/{repos}/branches", user, repos)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(BranchDto.class)
            .collectList()
            .block();
    }

    private List<CommitDto> getCommitsByBranch(String uri, OffsetDateTime lastUpdate) {

        List<CommitExtendedDto> commitsList = client.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CommitExtendedDto.class)
            .collectList()
            .block();

        if (commitsList == null) {
            return null;
        }

        commitsList.stream()
            .map(CommitExtendedDto::getCommit)
            .map(CommitDto::getAuthor)
            .forEach(author -> author.setDate(author.getDate().toLocalDateTime().atOffset(offset)));

        return commitsList.stream()
            .map(CommitExtendedDto::getCommit)
            .filter(elem -> elem.getAuthor().getDate().isAfter(lastUpdate))
            .toList();
    }
}
