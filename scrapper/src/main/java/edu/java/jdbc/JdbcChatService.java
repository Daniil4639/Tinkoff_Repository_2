package edu.java.jdbc;

import edu.java.domain.JdbcChatRepository;
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

    private final JdbcChatRepository jdbcChatRepository;
    private final String incorrectRequestParams = "Некорректные параметры запроса";

    public String addChat(Long chatId) throws IncorrectRequest {
        if (chatId == null) {
            throw new IncorrectRequest(incorrectRequestParams);
        }

        try {
            jdbcChatRepository.addChatRequest(chatId);
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

        try {
            jdbcChatRepository.deleteChatRequest(chatId);
        } catch (DoesNotExistException ex) {
            throw new DoesNotExistException("Чат не существует");
        }
    }

    public boolean isTrack(Long chatId) {
        return jdbcChatRepository.isWaitingTrack(chatId);
    }

    public boolean isUntrack(Long chatId) {
        return jdbcChatRepository.isWaitingUntrack(chatId);
    }

    public void makeTrack(Long chatId) {
        jdbcChatRepository.makeTrack(chatId);
    }

    public void makeUntrack(Long chatId) {
        jdbcChatRepository.makeUntrack(chatId);
    }

    public void deleteTrack(Long chatId) {
        jdbcChatRepository.deleteTrack(chatId);
    }

    public void deleteUnrack(Long chatId) {
        jdbcChatRepository.deleteUntrack(chatId);
    }
}
