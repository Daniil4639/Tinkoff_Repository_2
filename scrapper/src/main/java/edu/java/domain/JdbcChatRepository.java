package edu.java.domain;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addChatRequest(int chatId) throws ChatAlreadyExistsException {
        try {
            jdbcTemplate.execute("INSERT INTO Chats VALUES (" + chatId + ")");
        } catch (Exception ex) {
            throw new ChatAlreadyExistsException("Чат уже зарегистрирован");
        }
    }

    public void deleteChatRequest(int chatId) throws DoesNotExistException {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Chats WHERE chat_id=?",
                Integer.class, chatId);

        if (count == null || count == 0) {
            throw new DoesNotExistException("Чат не существует");
        }

        jdbcTemplate.execute("DELETE FROM Chat_Link_Connection WHERE chat_id=" + chatId);
        jdbcTemplate.execute("DELETE FROM Chats WHERE chat_id=" + chatId);
    }
}
