package edu.java.service;

import edu.java.clients.GitHubClient;
import edu.java.dto.github.CommitDto;
import edu.java.response.github.GitHubExtendedResponse;
import edu.java.response.github.GitHubResponse;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class GitHubService {
    private final GitHubClient gitHubClient;

    public GitHubResponse getGitHubInfo(String userName, String repositoryName) {
        return gitHubClient.getInfo(userName, repositoryName);
    }

    public String getUpdateInfo(String userName, String repositoryName,
        OffsetDateTime lastUpdateDate) {

        GitHubExtendedResponse response = gitHubClient.getExtendedInfo(userName, repositoryName,
            lastUpdateDate);

        StringBuilder result = new StringBuilder();
        result.append("Обновлен репозиторий \"").append(repositoryName).append("\"")
            .append(System.lineSeparator())
            .append("пользователя \"").append(userName).append("\":")
            .append(System.lineSeparator())
            .append("(ссылка: https://github.com/").append(userName).append("/")
            .append(repositoryName).append(")")
            .append(System.lineSeparator()).append(System.lineSeparator());

        for (int branchNum = 0; branchNum < response.getBranches().size(); branchNum++) {
            if (response.getCommitsByBranches().get(branchNum).isEmpty()) {
                continue;
            }
            result.append("Обновлена ветка \"").append(response.getBranches().get(branchNum))
                .append("\":").append(System.lineSeparator());

            for (int commitNum = 0; commitNum < response.getCommitsByBranches()
                .get(branchNum).size(); commitNum++) {

                CommitDto commit = response.getCommitsByBranches().get(branchNum).get(commitNum);

                result.append("Коммит №").append(commitNum + 1).append(") Сообщение: ")
                    .append(commit.getMessage()).append(System.lineSeparator());
                result.append("     Автор: ").append(commit.getAuthor().getName())
                    .append(System.lineSeparator());
                result.append("     Email: ").append(commit.getAuthor().getEmail())
                    .append(System.lineSeparator());
                result.append("     Дата: ").append(commit.getAuthor().getDate().toString())
                    .append(System.lineSeparator());
            }

            result.append(System.lineSeparator());
        }

        return result.toString();
    }
}
