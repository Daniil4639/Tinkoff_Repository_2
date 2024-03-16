package edu.java.jdbc;

import edu.java.domain.JdbcLinkDao;
import edu.java.response.api.LinkResponse;
import edu.java.response.api.ListLinksResponse;
import edu.java.response.resource.github.GitHubResponse;
import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcLinksService {

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private StackOverFlowService stackOverFlowService;
    @Autowired
    private JdbcLinkDao linkDao;

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public ListLinksResponse getLinksByChat(int chatId) {
        LinkResponse[] list = linkDao.getLinksByChatRequest(chatId);

        return new ListLinksResponse(list, (list == null) ? (0) : (list.length));
    }

    public boolean addLink(int chatId, String link) {
        Matcher matcher = gitHubPattern.matcher(link);

        if (matcher.find()) {
            GitHubResponse data = gitHubService.getGitHubInfo(matcher.group(1), matcher.group(2));

            linkDao.addLinkRequest(chatId, link, data.getCreationDate(), data.getLastUpdate());
            return true;
        }

        matcher = stackOverFlowPattern.matcher(link);

        if (matcher.find()) {
            StackOverFlowResponse data = stackOverFlowService.getStackOverFlowInfo(matcher.group(1));

            linkDao.addLinkRequest(chatId, link, data.getCreationDate(), data.getLastUpdate());
            return true;
        }

        return false;
    }

    public Integer getLinkId(String link) {
        return linkDao.getLinkId(link);
    }

    public void deleteLink(int chatId, int linkId) {
        linkDao.deleteLinkRequest(chatId, linkId);
    }
}
