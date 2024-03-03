package bot.client;

import bot.AbstractClientTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperClientChatTest extends AbstractClientTest {

    private final static String REGISTER_CHAT_RESPONSE = "Чат зарегистрирован";
    private final static String DELETE_CHAT_RESPONSE = "Чат успешно удалён";

    @Test
    public void registerChatTest() {
        stubFor(post(urlEqualTo("/tg-chat/12"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(REGISTER_CHAT_RESPONSE)));

        Mono<String> response = client.registerChat(12);

        assertThat(response.block()).isEqualTo(REGISTER_CHAT_RESPONSE);
    }

    @Test
    public void deleteChatTest() {
        stubFor(delete(urlEqualTo("/tg-chat/13"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(DELETE_CHAT_RESPONSE)));

        Mono<String> response = client.deleteChat(13);

        assertThat(response.block()).isEqualTo(DELETE_CHAT_RESPONSE);
    }
}
