package edu.java.clients;

import edu.java.api_exceptions.BadRequestException;
import edu.java.requests.api.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class BotClient extends Client {

    public BotClient(String url) {
        super(url);
    }

    public Mono<String> updateLink(String url, int[] tgChatIds, String text) {
        LinkUpdateRequest request = new LinkUpdateRequest(
            1, url, text, tgChatIds);

        return client.post()
            .uri("/updates")
            .body(Mono.just(request), LinkUpdateRequest.class)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(String.class)
            .retryWhen(retry);
    }
}
