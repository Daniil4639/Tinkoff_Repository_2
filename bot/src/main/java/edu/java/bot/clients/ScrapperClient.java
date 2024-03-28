package edu.java.bot.clients;

import edu.java.bot.api_exceptions.BadRequestException;
import edu.java.bot.api_exceptions.NotFoundException;
import edu.java.bot.requests.AddLinkRequest;
import edu.java.bot.requests.RemoveLinkRequest;
import edu.java.bot.responses.LinkResponse;
import edu.java.bot.responses.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class ScrapperClient extends Client {

    private final String tgChatIdParam = "tgChatId";
    private final String trackMapping = "/track";
    private final String untrackMapping = "/untrack";
    private final String tgChatPath = "/tg-chat/{id}";
    private final String linksPath = "/links";

    public ScrapperClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<String> registerChat(long chatId) {
        return client.post()
            .uri(tgChatPath, chatId)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(String.class)
            .retryWhen(retry);
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
            .bodyToMono(String.class)
            .retryWhen(retry);
    }

    public Mono<ListLinksResponse> getLinks(long id) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(ListLinksResponse.class)
            .retryWhen(retry);
    }

    public Mono<LinkResponse> addLink(String link, long id) {
        AddLinkRequest request = new AddLinkRequest(link);

        return client.post()
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
            .body(Mono.just(request), AddLinkRequest.class)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> Mono.error(new BadRequestException(response))
            )
            .bodyToMono(LinkResponse.class)
            .retryWhen(retry);
    }

    public Mono<LinkResponse> deleteLink(String link, long id) {
        RemoveLinkRequest request = new RemoveLinkRequest(link);

        return client.method(HttpMethod.DELETE)
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
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
            .bodyToMono(LinkResponse.class)
            .retryWhen(retry);
    }

    public void makeTrack(long id) {
        client.post()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retry)
            .block();
    }

    public void makeUntrack(long id) {
        client.post()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retry)
            .block();
    }

    public void deleteTrack(long id) {
        client.delete()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retry)
            .block();
    }

    public void deleteUntrack(long id) {
        client.delete()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retry)
            .block();
    }

    public Mono<Boolean> checkTrack(long id) {
        return client.get()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Boolean.class)
            .retryWhen(retry);
    }

    public Mono<Boolean> checkUntrack(long id) {
        return client.get()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Boolean.class)
            .retryWhen(retry);
    }
}
