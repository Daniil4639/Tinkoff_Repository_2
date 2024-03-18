package edu.java.scheduler;

import edu.java.response.api.LinkDataBaseInfo;
import edu.java.response.resource.GitHubResponse;
import edu.java.response.resource.StackOverFlowResponse;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcSchedulerService {

    private final GitHubService gitHubService;
    private final StackOverFlowService stackOverFlowService;
    private final JdbcSchedulerDao schedulerDao;

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public LinkDataBaseInfo[] getOldLinks(int minutesAgo) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime oldLinksTime = nowTime.minusMinutes(minutesAgo);

        LinkDataBaseInfo[] list = schedulerDao.getOldLinksRequest(oldLinksTime);

        for (LinkDataBaseInfo info: list) {
            schedulerDao.addTgChatsInfo(info);
        }

        if (list.length != 0) {
            updateLastCheckTime(list, nowTime);
        }

        return list;
    }

    public boolean hadUpdated(LinkDataBaseInfo info) {
        Matcher matcher = gitHubPattern.matcher(info.getUrl());

        if (matcher.find()) {
            GitHubResponse data = gitHubService.getGitHubInfo(matcher.group(1), matcher.group(2));

            return data.getLastUpdate().equals(info.getLastUpdate());
        }

        matcher = stackOverFlowPattern.matcher(info.getUrl());

        if (matcher.find()) {
            StackOverFlowResponse data = stackOverFlowService.getStackOverFlowInfo(matcher.group(1));

            return data.getLastUpdate().equals(info.getLastUpdate());
        }

        return false;
    }

    private void updateLastCheckTime(LinkDataBaseInfo[] list, LocalDateTime nowTime) {
        StringBuilder idStr = new StringBuilder();
        for (int elem: Arrays.stream(list)
            .mapToInt(LinkDataBaseInfo::getId).toArray()) {

            idStr.append(elem).append(", ");
        }

        schedulerDao.updateLinks(list, nowTime, (idStr.delete(idStr.length() - 2, idStr.length() - 1)).toString());
    }
}
