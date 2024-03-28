package edu.java.scrapper.service;

import edu.java.response.resource.github.GitHubResponse;
import edu.java.scrapper.AbstractClientTest;
import edu.java.service.GitHubService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GitHubServiceTest extends AbstractClientTest {

    private final static String GUT_HUB_JSON = """
        {
            "name": "test_repos",
            "html_url": "https://github.com/test_user/test_repos",
            "created_at": "2024-02-07T09:58:39Z",
            "pushed_at": "2024-02-07T10:00:21Z"
        }
        """;

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
}
