package bot.client;

import bot.AbstractClientTest;
import edu.java.exceptions.BadRequestException;
import edu.java.exceptions.NotFoundException;
import org.apache.http.HttpStatus;
import org.junit.Test;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperClientLinksTest extends AbstractClientTest {

    private final static String INCORRECT_DATA_RESPONSE = "Некорректные параметры запроса";
    private final static String INCORRECT_RESPONSE_BODY = """
        {
            "description": "Некорректные параметры запроса",
            "code": "400",
            "exceptionName": "edu.java.exceptions.IncorrectRequest",
            "exceptionMessage": "Некорректные параметры запроса"
        }
        """;
    private final static String NOT_FOUND_RESPONSE = "Ссылка не найдена";
    private final static String NOT_FOUND_RESPONSE_BODY = """
        {
            "description": "Ссылка не найдена",
            "code": "404",
            "exceptionName": "edu.java.exceptions.DoesNotExistException",
            "exceptionMessage": "Ссылка не найдена"
        }
        """;
    private final static String LINK_RESPONSE = """
        {
            "id": 1,
            "url": "testLink"
        }
        """;
    private final static String GET_LINKS_RESPONSE = """
        {
            "links": null,
            "size": 0
        }
        """;

    @Test
    public void getLinksSuccessTest() {
        stubFor(get(urlPathMatching("/links.*"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(GET_LINKS_RESPONSE)));

        StepVerifier.create(client.getLinks(12))
            .expectNextMatches(response -> {
                assertThat(response.getLinks()).isNull();
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void getLinksBadRequestTest() {
        stubFor(get(urlPathMatching("/links.*"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.getLinks(12))
            .expectErrorMatches(throwable -> {
                assertThat(((BadRequestException)throwable).message).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }

    @Test
    public void addLinkSuccessTest() {
        stubFor(post(urlPathMatching("/links.*"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(LINK_RESPONSE)));

        StepVerifier.create(client.addLink("testLink", 12))
            .expectNextMatches(response -> {
                assertThat(response.getUrl()).isEqualTo("testLink");
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void addLinkBadRequestTest() {
        stubFor(post(urlPathMatching("/links.*"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.addLink("testLink", 12))
            .expectErrorMatches(throwable -> {
                assertThat(((BadRequestException)throwable).message).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }

    @Test
    public void deleteLinkSuccessTest() {
        stubFor(delete(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(LINK_RESPONSE)));

        StepVerifier.create(client.deleteLink("testLink", 12))
            .expectNextMatches(response -> {
                assertThat(response.getUrl()).isEqualTo("testLink");
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void deleteLinkBadRequestTest() {
        stubFor(delete(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.deleteLink("testLink", 12))
            .expectErrorMatches(throwable -> {
                assertThat(((BadRequestException)throwable).message).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }

    @Test
    public void deleteLinkNotFoundTest() {
        stubFor(delete(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND)
                .withHeader("Content-Type", "application/json")
                .withBody(NOT_FOUND_RESPONSE_BODY)));

        StepVerifier.create(client.deleteLink("testLink", 12))
            .expectErrorMatches(throwable -> {
                assertThat(((NotFoundException)throwable).message).isEqualTo(NOT_FOUND_RESPONSE);
                return true;
            })
            .verify();
    }
}
