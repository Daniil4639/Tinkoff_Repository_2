package edu.java.clients;

import edu.java.dto.github.BranchDto;
import edu.java.dto.github.CommitDto;
import edu.java.dto.github.CommitExtendedDto;
import edu.java.response.resource.github.GitHubExtendedResponse;
import edu.java.response.resource.github.GitHubResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.http.MediaType;

public class GitHubClient extends Client {

    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public GitHubResponse getInfo(String user, String repos) {
        GitHubResponse response = client.get()
            .uri("/repos/{user}/{repos}", user, repos)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .retryWhen(retry)
            .log()
            .block();

        if (response == null) {
            return null;
        }

        response.setLastUpdate(response.getLastUpdate().toLocalDateTime().atOffset(ZoneId.systemDefault().getRules()
            .getOffset(Instant.now())));

        return response;
    }

    public GitHubExtendedResponse getExtendedInfo(String user, String repos,
        OffsetDateTime lastUpdate) {

        GitHubExtendedResponse response = new GitHubExtendedResponse();

        List<BranchDto> branches = getBranches(user, repos);

        for (BranchDto branch: branches) {
            List<CommitDto> commits = getCommitsByBranch(
                "/repos/" + user + "/" + repos + "/commits?sha=" + branch.getCommit().getSha(),
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
            .retryWhen(retry)
            .collectList()
            .block();
    }

    private List<CommitDto> getCommitsByBranch(String uri, OffsetDateTime lastUpdate) {

        List<CommitExtendedDto> commitsList = client.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(CommitExtendedDto.class)
            .retryWhen(retry)
            .collectList()
            .block();

        if (commitsList == null) {
            return null;
        }

        for (CommitExtendedDto commitExtendedDto : commitsList) {
            commitExtendedDto.getCommit().getAuthor().setDate(
                    commitExtendedDto.getCommit().getAuthor().getDate().toLocalDateTime()
                            .atOffset(ZoneId.systemDefault().getRules()
                                    .getOffset(Instant.now())));
        }

        return commitsList.stream()
            .map(CommitExtendedDto::getCommit)
            .filter(elem -> elem.getAuthor().getDate().isAfter(lastUpdate))
            .toList();
    }
}
