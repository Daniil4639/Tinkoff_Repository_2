package edu.java.clients;

import edu.java.api_exceptions.ApiResponseErrorException;
import edu.java.requests.api.LinkUpdateRequest;
import edu.java.response.api.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class BotClient extends Client {

    public BotClient(String url) {
        super(url);
    }

    public ApiErrorResponse updateLink(String url, int[] tgChatIds) {
        LinkUpdateRequest request = new LinkUpdateRequest(
            1, url, "Обновить ссылку", tgChatIds);

        return client.post()
            .uri("/updates")
            .body(Mono.just(request), LinkUpdateRequest.class)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new ApiResponseErrorException(response))
            )
            .bodyToMono(ApiErrorResponse.class)
            .onErrorResume(ApiResponseErrorException.class, ex -> ex.response.bodyToMono(ApiErrorResponse.class))
            .block();
    }
}
