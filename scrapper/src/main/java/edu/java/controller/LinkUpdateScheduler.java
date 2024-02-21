package edu.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@EnableScheduling
public class LinkUpdateScheduler {

    public LinkUpdateScheduler() {}

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("Идет проверка обновлений!");
    }
}
