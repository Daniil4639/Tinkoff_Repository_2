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

    public Mono<LinkResponseList> getLinks() {
        return client.get()
            .uri(linksPath)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(BotApiError.class)
                    .map(BotApiError::getExceptionMessage)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(LinkResponseList.class);
    }

    public Mono<LinkResponse> addLink(String link) {
        return client.post()
            .uri(linksPath)
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

    public Mono<LinkResponse> deleteLink(String link) {
        return client.method(HttpMethod.DELETE)
            .uri(linksPath)
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
}
