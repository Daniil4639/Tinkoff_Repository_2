package edu.java.clients.bot;

import edu.java.requests.LinkUpdateRequest;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import reactor.core.publisher.Mono;

@Slf4j
public class BotKafkaClient implements BotClient {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final String topicName;

    public BotKafkaClient(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate, String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public Mono<String> updateLink(String url, int[] tgChatIds, String text) {

        LinkUpdateRequest request = new LinkUpdateRequest(
            1, url, text, tgChatIds);

        CompletableFuture<SendResult<String, LinkUpdateRequest>> future = kafkaTemplate.send(
            topicName, request);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Обработа сообщение: " + result.getRecordMetadata().toString());
            } else {
                log.info("Ошибка во время обработки обновления: " + ex.getMessage());
            }
        });

        return Mono.just("Обновление отправлено в очередь!");
    }
}
