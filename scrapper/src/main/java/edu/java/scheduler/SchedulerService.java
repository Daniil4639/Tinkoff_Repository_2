package edu.java.scheduler;

import edu.java.response.github.GitHubResponse;
import edu.java.response.sof.StackOverFlowResponse;
import edu.java.responses.LinkDataBaseInfo;
import edu.java.scheduler.daos.SchedulerDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class SchedulerService {

    private final GitHubService gitHubService;
    private final StackOverFlowService stackOverFlowService;
    private final SchedulerDao schedulerDao;

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public LinkDataBaseInfo[] getOldLinks(int minutesAgo) {
        OffsetDateTime nowTime = OffsetDateTime.now();
        OffsetDateTime oldLinksTime = nowTime.minusMinutes(minutesAgo);

        LinkDataBaseInfo[] list = schedulerDao.getOldLinksRequest(oldLinksTime);

        for (LinkDataBaseInfo info: list) {
            schedulerDao.addTgChatsInfo(info);
        }

        if (list.length != 0) {
            updateLastCheckTime(list, nowTime);
        }

        return list;
    }

    public Pair<Boolean, String> hadUpdated(LinkDataBaseInfo info) {
        Matcher matcher = gitHubPattern.matcher(info.getUrl());

        if (matcher.find()) {
            return updateByGit(info, matcher);
        }

        matcher = stackOverFlowPattern.matcher(info.getUrl());

        if (matcher.find()) {
            return updateBySof(info, matcher);
        }

        return new ImmutablePair<>(false, null);
    }

    private Pair<Boolean, String> updateByGit(LinkDataBaseInfo info, Matcher matcher) {
        GitHubResponse data = gitHubService.getGitHubInfo(matcher.group(1), matcher.group(2));

        boolean hadUpdate = !data.getLastUpdate().equals(info.getLastUpdate());
        if (!hadUpdate) {
            return new ImmutablePair<>(false, null);
        }

        schedulerDao.updateLinkDate(info.getId(), data.getLastUpdate());
        return new ImmutablePair<>(true, gitHubService
            .getUpdateInfo(matcher.group(1), matcher.group(2), info.getLastUpdate()));
    }

    private Pair<Boolean, String> updateBySof(LinkDataBaseInfo info, Matcher matcher) {
        StackOverFlowResponse data = stackOverFlowService.getStackOverFlowInfo(matcher.group(1));

        boolean hadUpdate =  !data.getLastUpdate().equals(info.getLastUpdate());
        if (!hadUpdate) {
            return new ImmutablePair<>(false, null);
        }

        schedulerDao.updateLinkDate(info.getId(), data.getLastUpdate());
        return new ImmutablePair<>(true, stackOverFlowService
            .getUpdateInfo(matcher.group(1), info.getLastUpdate(), data));
    }

    private void updateLastCheckTime(LinkDataBaseInfo[] list, OffsetDateTime nowTime) {

        schedulerDao.updateLastCheck(list, nowTime);
    }
}
