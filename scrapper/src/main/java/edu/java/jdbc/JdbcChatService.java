package edu.java.jdbc;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import edu.java.domain.JdbcChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JdbcChatService {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    public String addChat(long chatId) {
        try {
            jdbcChatRepository.addChatRequest(chatId);
        } catch (ChatAlreadyExistsException ex) {
            return ex.getMessage();
        }

        return "Чат зарегистрирован!";
    }

    public boolean deleteChat(long chatId) {
        try {
            jdbcChatRepository.deleteChatRequest(chatId);
        } catch (DoesNotExistException ex) {
            return false;
        }

        return true;
    }

    public boolean isTrack(long chatId) {
        return jdbcChatRepository.isWaitingTrack(chatId);
    }

    public boolean isUntrack(long chatId) {
        return jdbcChatRepository.isWaitingUntrack(chatId);
    }

    public void makeTrack(long chatId) {
        jdbcChatRepository.makeTrack(chatId);
    }

    public void makeUntrack(long chatId) {
        jdbcChatRepository.makeUntrack(chatId);
    }

    public void deleteTrack(long chatId) {
        jdbcChatRepository.deleteTrack(chatId);
    }

    public void deleteUnrack(long chatId) {
        jdbcChatRepository.deleteUntrack(chatId);
    }
}
