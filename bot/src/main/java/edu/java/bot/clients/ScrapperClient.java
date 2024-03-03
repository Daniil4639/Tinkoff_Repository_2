package edu.java.bot.clients;

import edu.java.bot.api_exceptions.BadRequestException;
import edu.java.bot.api_exceptions.NotFoundException;
import edu.java.bot.requests.AddLinkRequest;
import edu.java.bot.requests.RemoveLinkRequest;
import edu.java.bot.responses.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class ScrapperClient extends Client {

    private final String tgChatPath = "/tg-chat/{id}";
    private final String linksPath = "/links";

    public ScrapperClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<String> registerChat(int chatId) {
        return client.post()
            .uri(tgChatPath, chatId)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(String.class);
    }

    public Mono<String> deleteChat(int chatId) {
        return client.delete()
            .uri(tgChatPath, chatId)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> Mono.error(new NotFoundException(response))
            )
            .bodyToMono(String.class);
    }

    public Mono<ListLinksResponse> getLinks() {
        return client.get()
            .uri(linksPath)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(ListLinksResponse.class);
    }

    public Mono<String> addLink(String link) {
        AddLinkRequest request = new AddLinkRequest(link);

        return client.post()
            .uri(linksPath)
            .body(Mono.just(request), AddLinkRequest.class)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(String.class);
    }

    public Mono<String> deleteLink(String link) {
        RemoveLinkRequest request = new RemoveLinkRequest(link);

        return client.method(HttpMethod.DELETE)
            .uri(linksPath)
            .body(Mono.just(request), AddLinkRequest.class)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> Mono.error(new NotFoundException(response))
            )
            .bodyToMono(String.class);
    }
}
