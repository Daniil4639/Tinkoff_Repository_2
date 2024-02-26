package edu.java.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class LinkUpdateScheduler {

    @Scheduled(fixedDelayString = "#{@schedulerInterval}")
    public void update() {
        log.info("Идет проверка обновлений!");
    }
}
