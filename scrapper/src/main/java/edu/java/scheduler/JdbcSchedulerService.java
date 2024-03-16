package edu.java.scheduler;

import edu.java.response.api.LinkDataBaseInfo;
import edu.java.response.resource.github.GitHubResponse;
import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcSchedulerService {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private StackOverFlowService stackOverFlowService;
    @Autowired
    private JdbcSchedulerDao schedulerDao;

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public LinkDataBaseInfo[] getOldLinks(int minutesAgo) {
        return schedulerDao.getOldLinksRequest(minutesAgo);
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

        boolean hadUpdate =  !data.getLastUpdate().equals(info.getLastUpdate());
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
}