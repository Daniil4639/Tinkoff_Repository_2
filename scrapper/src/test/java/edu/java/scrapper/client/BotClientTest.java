package edu.java.scrapper.client;

import edu.java.clients.BotClient;
import edu.java.exceptions.BadRequestException;
import edu.java.scrapper.AbstractClientTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BotClientTest extends AbstractClientTest {

    private final static String CORRECT_RESPONSE = "Обновление обработано";
    private final static String INCORRECT_DATA_RESPONSE = "Некорректные параметры запроса";
    private final static String INCORRECT_RESPONSE_BODY = """
        {
            "description": "Некорректные параметры запроса",
            "code": "400",
            "exceptionName": "edu.java.exceptions.IncorrectRequest",
            "exceptionMessage": "Некорректные параметры запроса"
        }
        """;

    @Autowired
    private BotClient client;

    @Test
    public void updateLinkSuccessTest() throws BadRequestException {
        stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(CORRECT_RESPONSE)));

        StepVerifier.create(client.updateLink("testLink", new int[] {1, 2, 3}, ""))
            .expectNextMatches(response -> {
                assertThat(response).isEqualTo(CORRECT_RESPONSE);
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void updateLinkFailTest() throws BadRequestException {
        stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.updateLink("testLink", new int[] {1, 2, 3}, ""))
            .expectErrorMatches(throwable -> {
                assertThat(throwable.getMessage()).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }
}
