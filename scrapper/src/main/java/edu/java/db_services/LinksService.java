package edu.java.db_services;

import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.domain.interfaces.LinkDao;
import edu.java.response.api.LinkResponse;
import edu.java.response.api.ListLinksResponse;
import edu.java.response.resource.github.GitHubResponse;
import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class LinksService {

    private final GitHubService gitHubService;
    private final StackOverFlowService stackOverFlowService;
    private final LinkDao linkDao;
    private final String incorrectRequestParams = "Некорректные параметры запроса";

    private final Pattern gitHubPattern =
        Pattern.compile("https://github.com/([a-zA-Z0-9-_.,]+)/([a-zA-Z0-9-_.,]+)");
    private final Pattern stackOverFlowPattern =
        Pattern.compile("https://stackoverflow.com/questions/([0-9]+)");

    public ListLinksResponse getLinksByChat(Integer chatId) throws IncorrectChatOperationRequest {
        if (chatId == null) {
            throw new IncorrectChatOperationRequest(incorrectRequestParams);
        }

        LinkResponse[] list = linkDao.getLinksByChatRequest(chatId);

        return new ListLinksResponse(list, (list == null) ? (0) : (list.length));
    }

    @SuppressWarnings("ReturnCount")
    public void addLink(Integer chatId, String link) throws IncorrectChatOperationRequest {
        if (chatId == null || link == null) {
            throw new IncorrectChatOperationRequest(incorrectRequestParams);
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

        throw new IncorrectChatOperationRequest(incorrectRequestParams);
    }

    public Long getLinkId(String link) {
        return linkDao.getLinkId(link);
    }

    public void deleteLink(Integer chatId, String link) throws IncorrectChatOperationRequest,
        DoesNotExistException {

        if (chatId == null || link == null) {
            throw new IncorrectChatOperationRequest(incorrectRequestParams);
        }

        Long linkId = getLinkId(link);

        if (linkId == null) {
            throw new DoesNotExistException("Ссылка не найдена");
        }

        linkDao.deleteLinkRequest(chatId, linkId);
    }
}
