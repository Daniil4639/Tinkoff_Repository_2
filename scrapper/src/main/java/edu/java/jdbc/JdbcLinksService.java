package edu.java.jdbc;

import edu.java.domain.JdbcLinkDao;
import edu.java.exceptions.DoesNotExistException;
import edu.java.exceptions.IncorrectRequest;
import edu.java.response.GitHubResponse;
import edu.java.response.StackOverFlowResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.LinkResponseList;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcLinksService {

    private final GitHubService gitHubService;
    private final StackOverFlowService stackOverFlowService;
    private final JdbcLinkDao linkDao;
    private final String incorrectRequestParams = "Некорректные параметры запроса";

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public LinkResponseList getLinksByChat(Integer chatId) throws IncorrectRequest {
        if (chatId == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        LinkResponse[] list = linkDao.getLinksByChatRequest(chatId);

        return new LinkResponseList(list, (list == null) ? (0) : (list.length));
    }

    @SuppressWarnings("ReturnCount")
    public void addLink(Integer chatId, String link) throws IncorrectRequest {
        if (chatId == null || link == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        Matcher matcher = gitHubPattern.matcher(link);

        if (matcher.find()) {
            GitHubResponse data = gitHubService.getGitHubInfo(matcher.group(1), matcher.group(2));

            linkDao.addLinkRequest(chatId, link, data.getCreationDate(), data.getLastUpdate());
            return;
        }

        matcher = stackOverFlowPattern.matcher(link);

        if (matcher.find()) {
            StackOverFlowResponse data = stackOverFlowService.getStackOverFlowInfo(matcher.group(1));

            linkDao.addLinkRequest(chatId, link, data.getCreationDate(), data.getLastUpdate());
            return;
        }

        throw new IncorrectRequest(incorrectRequestParams);
    }

    public Integer getLinkId(String link) {
        return linkDao.getLinkId(link);
    }

    public void deleteLink(Integer chatId, String link) throws IncorrectRequest,
        DoesNotExistException {

        if (chatId == null || link == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        Integer linkId = getLinkId(link);

        if (linkId == null) {
            throw new DoesNotExistException("Ссылка не найдена");
        }

        linkDao.deleteLinkRequest(chatId, linkId);
    }
}
