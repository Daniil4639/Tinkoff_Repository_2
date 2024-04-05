package edu.java.bot.kafka;

import edu.java.bot.service.MessageService;
import edu.java.requests.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KafkaUpdateListener {

    private final MessageService service;

    @RetryableTopic(attempts = "2", dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR)
    @KafkaListener(topics = "${app.kafka.topic-name}")
    public void listenGroup(LinkUpdateRequest request) {
        service.sendUpdate(request);
    }

    @DltHandler
    public void handleDlt(LinkUpdateRequest request) {
        log.info("Некорретный запрос отправлен в DLT!");
    }
}
