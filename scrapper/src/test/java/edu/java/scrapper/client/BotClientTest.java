package edu.java.scrapper.client;

import edu.java.clients.BotClient;
import edu.java.scrapper.AbstractClientTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BotClientTest extends AbstractClientTest {

    private final static String CORRECT_RESPONSE = "Обновление обработано";

    @Autowired
    private BotClient client;

    @Test
    public void updateLinkTest() {
        stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(CORRECT_RESPONSE)));

        Mono<String> response = client.updateLink("testLink", new int[] {1, 2, 3});

        assertThat(response.block()).isEqualTo(CORRECT_RESPONSE);
    }
}
