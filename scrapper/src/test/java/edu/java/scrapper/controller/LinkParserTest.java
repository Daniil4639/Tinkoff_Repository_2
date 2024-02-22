package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import edu.java.clients.GitHubClient;
import io.restassured.RestAssured;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = {ScrapperApplication.class})
@RunWith(SpringRunner.class)
@WireMockTest(httpPort = 80)
public class LinkParserTest {

    /*private static final String gitHubJson = """
        "id": 1148,
        "name": "Test_Repository",
        "created_at": "2024-02-07T09:58:39Z",
        "updated_at": "2024-02-07T10:00:21Z"
        """;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
        .build();

    @MockBean
    private GitHubClient gitHubClient;

    @Test
    public void linkParseTest() {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();

        wireMockExtension.stubFor(get(urlEqualTo("/TestName/TestRepository"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "testGitHubRequest/json")
                .withBody(gitHubJson)));

        //System.out.println(gitHubClient.getGitHubInfo("/TestName/TestRepository"));
    }*/
}
