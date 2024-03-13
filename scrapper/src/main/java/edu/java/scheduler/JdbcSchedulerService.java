package edu.java.scheduler;

import edu.java.response.api.LinkDataBaseInfo;
import edu.java.response.resource.GitHubResponse;
import edu.java.response.resource.StackOverFlowResponse;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
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
}
