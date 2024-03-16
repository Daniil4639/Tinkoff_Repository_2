package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.response.api.LinkDataBaseInfo;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class LinkUpdateScheduler {

    @Autowired
    private JdbcSchedulerService schedulerService;
    @Autowired
    private BotClient client;

    @Scheduled(fixedDelayString = "#{@schedulerInterval}")
    public void update() {
        LinkDataBaseInfo[] list;

        try {
            list = schedulerService.getOldLinks(1);

            if (list == null) {
                throw new Exception("");
            }
        } catch (Exception ex) {
            return;
        }

        log.info("Ссылок давно не обновлялось: " + list.length);

        for (LinkDataBaseInfo linkInfo: list) {
            Pair<Boolean, String> updateInfo = schedulerService.hadUpdated(linkInfo);
            if (updateInfo.getLeft()) {

                log.info(client.updateLink(linkInfo.getUrl(), Arrays.stream(linkInfo.getTgChatIds())
                    .mapToInt(Integer::intValue).toArray(), updateInfo.getRight()).block());
            }
        }
    }
}
