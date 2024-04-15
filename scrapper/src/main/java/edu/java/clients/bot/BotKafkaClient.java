package edu.java.clients.bot;

import edu.java.requests.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;

@Slf4j
public record BotKafkaClient(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate, String topicName)
    implements BotClient {

    @Override
    public Mono<String> updateLink(String url, int[] tgChatIds, String text) {

        LinkUpdateRequest request = new LinkUpdateRequest(
            1, url, text, tgChatIds);

        try {
            kafkaTemplate.send(topicName, request);
            log.info("Обновление отправлено в топик: " + topicName);
        } catch (Exception ex) {
            log.info("Ошибка во время отправки обновления: " + ex.getMessage());
        }

        return Mono.just("Обновление отправлено в очередь!");
    }
}
