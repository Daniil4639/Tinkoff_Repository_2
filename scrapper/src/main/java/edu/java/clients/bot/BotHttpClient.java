package edu.java.clients.bot;

import edu.java.clients.Client;
import edu.java.exceptions.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import edu.java.responses.BotApiError;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class BotHttpClient extends Client implements BotClient {

    public BotHttpClient(String url) {
        super(url);
    }

    @Override
    public Mono<String> updateLink(String url, int[] tgChatIds, String text)
        throws BadRequestException {

        LinkUpdateRequest request = new LinkUpdateRequest(
            1, url, text, tgChatIds);

        return client.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(String.class);
    }
}
