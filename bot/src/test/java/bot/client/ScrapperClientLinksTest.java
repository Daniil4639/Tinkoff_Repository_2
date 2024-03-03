package bot.client;

import bot.AbstractClientTest;
import edu.java.bot.responses.ListLinksResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperClientLinksTest extends AbstractClientTest {

    private final static String ADD_LINK_RESPONSE = "Ссылка успешно добавлена";
    private final static String DELETE_LINK_RESPONSE = "Ссылка успешно убрана";
    private final static String GET_LINKS_RESPONSE = """
        {
            "links": null,
            "size": 0
        }
        """;

    @Test
    public void getLinksTest() {
        stubFor(get(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(GET_LINKS_RESPONSE)));

        Mono<ListLinksResponse> response = client.getLinks();

        assertThat(response.block().getLinks()).isNull();
    }

    @Test
    public void addLinkTest() {
        stubFor(post(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(ADD_LINK_RESPONSE)));

        Mono<String> response = client.addLink("testLink");

        assertThat(response.block()).isEqualTo(ADD_LINK_RESPONSE);
    }

    @Test
    public void deleteLinkTest() {
        stubFor(delete(urlEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(DELETE_LINK_RESPONSE)));

        Mono<String> response = client.deleteLink("testLink");

        assertThat(response.block()).isEqualTo(DELETE_LINK_RESPONSE);
    }
}
