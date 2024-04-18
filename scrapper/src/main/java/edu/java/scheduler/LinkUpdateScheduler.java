package edu.java.scheduler;

import edu.java.clients.bot.BotClient;
import edu.java.exceptions.BadRequestException;
import edu.java.responses.LinkDataBaseInfo;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdateScheduler {

    private final SchedulerService schedulerService;
    private final BotClient client;

    @Scheduled(fixedDelayString = "#{@schedulerInterval}")
    public void update() throws BadRequestException {
        LinkDataBaseInfo[] list;

        list = schedulerService.getOldLinks(2);

        if (list == null) {
            return;
        }
        log.info("Ссылок давно не обновлялось: " + list.length);

        for (LinkDataBaseInfo linkInfo: list) {
            Pair<Boolean, String> updateInfo = schedulerService.hadUpdated(linkInfo);
            if (updateInfo.getLeft()) {

                try {
                    log.info(client.updateLink(linkInfo.getUrl(), Arrays.stream(linkInfo.getTgChatIds())
                        .mapToInt(Integer::intValue).toArray(), updateInfo.getRight()).block());
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }
}
