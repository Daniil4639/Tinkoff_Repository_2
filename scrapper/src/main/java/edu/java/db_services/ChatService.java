package edu.java.db_services;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.domain.interfaces.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final String incorrectRequestParams = "Некорректные параметры запроса";

    public String addChat(Long chatId) throws IncorrectChatOperationRequest {
        if (chatId == null) {
            throw new IncorrectChatOperationRequest(incorrectRequestParams);
        }

        try {
            chatRepository.addChatRequest(chatId);
        } catch (ChatAlreadyExistsException ex) {
            return ex.getMessage();
        }

        return "Чат зарегистрирован!";
    }

    public void deleteChat(Long chatId) throws IncorrectChatOperationRequest,
        DoesNotExistException {

        if (chatId == null) {
            throw new IncorrectChatOperationRequest(incorrectRequestParams);
        }

        try {
            chatRepository.deleteChatRequest(chatId);
        } catch (DoesNotExistException ex) {
            throw new DoesNotExistException("Чат не существует");
        }
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
