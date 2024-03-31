package edu.java.scrapper.service;

import edu.java.response.github.GitHubResponse;
import edu.java.scrapper.AbstractClientTest;
import edu.java.service.GitHubService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GitHubServiceTest extends AbstractClientTest {

    private static final OffsetDateTime time = OffsetDateTime.now();

    private final static String GUT_HUB_JSON = """
        {
            "name": "test_repos",
            "html_url": "https://github.com/test_user/test_repos",
            "created_at": "2024-02-07T09:58:39Z",
            "pushed_at": "2024-02-07T10:00:21Z"
        }
        """;

    private final static String BRANCHES_RESPONSE = """
        [
            {
                "name": "main",
                "commit": {
                    "sha": "1234",
                    "url": "random_link"
                }
            }
        ]
        """;

    private final static String COMMITS_RESPONSE = String.format("""
        [
            {
                "sha": "1234",
                         "commit": {
                             "author": {
                                 "name": "someone",
                                 "email": "###",
                                 "date": "%s"
                             },
                             "message": "сообщение"
                         }
            }
        ]
        """, time.toString());

    private final static String CORRECT_EXTENDED_ANSWER = String.format("""
Обновлен репозиторий "test_repos"
пользователя "test_user":
(ссылка: https://github.com/test_user/test_repos)

Обновлена ветка "main":
Коммит №1) Сообщение: сообщение
     Автор: someone
     Email: ###
     Дата: %s

""", time.minusHours(3).toString());

    @Autowired
    private GitHubService gitHubService;

    @Test
    public void gitHubServiceTest() {
        stubFor(get(urlEqualTo("/repos/test_user/test_repos"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(GUT_HUB_JSON)));

        GitHubResponse response = gitHubService
            .getGitHubInfo("test_user", "test_repos");

        assertThat(response.getReposName()).isEqualTo("test_repos");
        assertThat(response.getReposLink()).isEqualTo("https://github.com/test_user/test_repos");
        assertThat(response.getCreationDate().toString()).isEqualTo("2024-02-07T09:58:39Z");
        assertThat(response.getLastUpdate().toString()).isEqualTo("2024-02-07T10:00:21+03:00");
    }

    @Test
    public void gitHubExtendedServiceTest() {
        stubFor(get(urlEqualTo("/repos/test_user/test_repos/branches"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(BRANCHES_RESPONSE)));

        stubFor(get(urlEqualTo("/repos/test_user/test_repos/commits?sha=1234"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(COMMITS_RESPONSE)));

        String result = gitHubService.getUpdateInfo("test_user", "test_repos",
            time.minusDays(50));

        assertThat(result.replace(System.lineSeparator(), "\n"))
            .isEqualTo(CORRECT_EXTENDED_ANSWER);
    }
}
