package edu.java.jdbc;

import edu.java.domain.jooq.JooqChatRepository;
import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.exceptions.DoesNotExistException;
import edu.java.exceptions.IncorrectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcChatService {

    private final JooqChatRepository chatRepository;
    private final String incorrectRequestParams = "Некорректные параметры запроса";

    public String addChat(Long chatId) throws IncorrectRequest {
        if (chatId == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        try {
            chatRepository.addChatRequest(chatId);
        } catch (ChatAlreadyExistsException ex) {
            return ex.getMessage();
        }

        return "Чат зарегистрирован!";
    }

    public void deleteChat(Long chatId) throws IncorrectRequest,
        DoesNotExistException {

        if (chatId == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        chatRepository.deleteChatRequest(chatId);
    }

    public boolean isTrack(Long chatId) {
        return chatRepository.isWaitingTrack(chatId);
    }

    public boolean isUntrack(Long chatId) {
        return chatRepository.isWaitingUntrack(chatId);
    }

    public void makeTrack(Long chatId) {
        chatRepository.makeTrack(chatId);
    }

    public void makeUntrack(Long chatId) {
        chatRepository.makeUntrack(chatId);
    }

    public void deleteTrack(Long chatId) {
        chatRepository.deleteTrack(chatId);
    }

    public void deleteUnrack(Long chatId) {
        chatRepository.deleteUntrack(chatId);
    }
}
