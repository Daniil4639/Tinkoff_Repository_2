package edu.java.jdbc;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import edu.java.domain.JdbcChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    public String addChat(int chatId) {
        try {
            jdbcChatRepository.addChatRequest(chatId);
        } catch (ChatAlreadyExistsException ex) {
            return ex.getMessage();
        }

        return "Чат зарегистрирован!";
    }

    public boolean deleteChat(int chatId) {
        try {
            jdbcChatRepository.deleteChatRequest(chatId);
        } catch (DoesNotExistException ex) {
            return false;
        }

        return true;
    }
}
