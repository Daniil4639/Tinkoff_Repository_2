package bot.client;

import bot.AbstractClientTest;
import edu.java.exceptions.BadRequestException;
import edu.java.exceptions.NotFoundException;
import org.apache.http.HttpStatus;
import org.junit.Test;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperClientChatTest extends AbstractClientTest {

    private final static String REGISTER_CHAT_RESPONSE = "Чат зарегистрирован";
    private final static String DELETE_CHAT_RESPONSE = "Чат успешно удалён";
    private final static String INCORRECT_DATA_RESPONSE = "Некорректные параметры запроса";
    private final static String INCORRECT_RESPONSE_BODY = """
        {
            "description": "Некорректные параметры запроса",
            "code": "400",
            "exceptionName": "edu.java.exceptions.IncorrectRequest",
            "exceptionMessage": "Некорректные параметры запроса"
        }
        """;
    private final static String NOT_FOUND_RESPONSE = "Чат не найден";
    private final static String NOT_FOUND_RESPONSE_BODY = """
        {
            "description": "Чат не найден",
            "code": "404",
            "exceptionName": "edu.java.exceptions.DoesNotExistException",
            "exceptionMessage": "Чат не найден"
        }
        """;

    @Test
    public void registerChatSuccessTest() {
        stubFor(post(urlEqualTo("/tg-chat/12"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(REGISTER_CHAT_RESPONSE)));

        StepVerifier.create(client.registerChat(12))
            .expectNextMatches(response -> {
                assertThat(response).isEqualTo(REGISTER_CHAT_RESPONSE);
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void registerChatBadRequestTest() {
        stubFor(post(urlEqualTo("/tg-chat/12"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.registerChat(12))
            .expectErrorMatches(throwable -> {
                assertThat(((BadRequestException)throwable).message).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }

    @Test
    public void deleteChatSuccessTest() {
        stubFor(delete(urlEqualTo("/tg-chat/13"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(DELETE_CHAT_RESPONSE)));

        StepVerifier.create(client.deleteChat(13))
            .expectNextMatches(response -> {
                assertThat(response).isEqualTo(DELETE_CHAT_RESPONSE);
                return true;
            })
            .verifyComplete();
    }

    @Test
    public void deleteChatBadRequestTest() {
        stubFor(delete(urlEqualTo("/tg-chat/13"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_BAD_REQUEST)
                .withHeader("Content-Type", "application/json")
                .withBody(INCORRECT_RESPONSE_BODY)));

        StepVerifier.create(client.deleteChat(13))
            .expectErrorMatches(throwable -> {
                System.out.println(throwable.getMessage());
                System.out.println(throwable.toString());
                assertThat(((BadRequestException)throwable).message).isEqualTo(INCORRECT_DATA_RESPONSE);
                return true;
            })
            .verify();
    }

    @Test
    public void deleteChatNotFoundTest() {
        stubFor(delete(urlEqualTo("/tg-chat/13"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND)
                .withHeader("Content-Type", "application/json")
                .withBody(NOT_FOUND_RESPONSE_BODY)));

        StepVerifier.create(client.deleteChat(13))
            .expectErrorMatches(throwable -> {
                assertThat(((NotFoundException)throwable).message).isEqualTo(NOT_FOUND_RESPONSE);
                return true;
            })
            .verify();
    }
}
