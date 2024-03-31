package edu.java.bot.clients;

import edu.java.exceptions.BadRequestException;
import edu.java.exceptions.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.BotApiError;
import edu.java.responses.LinkResponse;
import edu.java.responses.LinkResponseList;
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
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(String.class);
    }

    public Mono<String> deleteChat(int chatId) {
        return client.delete()
            .uri(tgChatPath, chatId)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new NotFoundException(message)))
            )
            .bodyToMono(String.class);
    }

    public Mono<LinkResponseList> getLinks(long id) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(LinkResponseList.class);
    }

    public Mono<LinkResponse> addLink(String link, long id) {
        return client.post()
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
            .bodyValue(new AddLinkRequest(link))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> deleteLink(String link, long id) {
        return client.method(HttpMethod.DELETE)
            .uri(uriBuilder -> uriBuilder
                .path(linksPath)
                .queryParam(tgChatIdParam, id)
                .build())
            .bodyValue(new RemoveLinkRequest(link))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new NotFoundException(message)))
            )
            .bodyToMono(LinkResponse.class);
    }

    public void makeTrack(long id) {
        client.post()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public void makeUntrack(long id) {
        client.post()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public void deleteTrack(long id) {
        client.delete()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public void deleteUntrack(long id) {
        client.delete()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public Mono<Boolean> checkTrack(long id) {
        return client.get()
            .uri(tgChatPath + trackMapping, id)
            .retrieve()
            .bodyToMono(Boolean.class);
    }

    public Mono<Boolean> checkUntrack(long id) {
        return client.get()
            .uri(tgChatPath + untrackMapping, id)
            .retrieve()
            .bodyToMono(Boolean.class);
    }
}
