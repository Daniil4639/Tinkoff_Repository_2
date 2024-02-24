package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@AutoConfigureWebTestClient(timeout = "10000")
@WireMockTest
public class LinkParserTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String gitHubJson = """
        "id": 1148,
        "name": "Test_Repository",
        "created_at": "2024-02-07T09:58:39Z",
        "updated_at": "2024-02-07T10:00:21Z"
        """;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.gitHubBaseUrl", wireMockExtension::baseUrl);
    }

    @AfterEach
    void afterEach() {
        wireMockExtension.resetAll();
    }

    @Test
    public void linkParseTest() {

        wireMockExtension.stubFor(WireMock.get(WireMock.urlEqualTo(
                "/repos/testUser/Test_Repository")
            ).willReturn(aResponse()
                .withStatus(200)
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            ));

        webTestClient.get().uri("/repos/testUser/Test_Repository")
            .exchange()
            .expectStatus()
            .isOk();
    }
}
