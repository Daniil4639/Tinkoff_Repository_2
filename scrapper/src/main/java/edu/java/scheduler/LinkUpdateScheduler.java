package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.jdbc.JdbcLinksService;
import edu.java.response.api.LinkDataBaseInfo;
import edu.java.response.api.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;

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
        LinkDataBaseInfo[] list = schedulerService.getOldLinks(2);

        log.info("Ссылок давно не обновлялось: " + ((list==null)?(0):(list.length)));

        if (list==null) {
            return;
        }

        for (LinkDataBaseInfo linkInfo: list) {
            if (schedulerService.hadUpdated(linkInfo)) {
                log.info(client.updateLink(linkInfo.getUrl(), Arrays.stream(linkInfo.getTgChatIds())
                    .mapToInt(Integer::intValue).toArray()).block());
            }
        }
    }
}
