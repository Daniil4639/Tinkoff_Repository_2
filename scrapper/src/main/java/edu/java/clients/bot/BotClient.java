package edu.java.clients.bot;

import edu.java.exceptions.BadRequestException;
import reactor.core.publisher.Mono;

public interface BotClient {

    Mono<String> updateLink(String url, int[] tgChatIds, String text)
        throws BadRequestException;
}
