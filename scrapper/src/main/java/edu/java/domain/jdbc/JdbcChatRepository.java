package edu.java.domain.jdbc;

import edu.java.domain.interfaces.ChatRepository;
import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.exceptions.DoesNotExistException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addChatRequest(long chatId) throws ChatAlreadyExistsException {
        try {
            jdbcTemplate.update("INSERT INTO Chats VALUES (?, 0, 0, ?)",
                chatId, Timestamp.valueOf(LocalDateTime.now()));
        } catch (Exception ex) {
            throw new ChatAlreadyExistsException("Чат уже зарегистрирован");
        }
    }

    @Override
    public void deleteChatRequest(long chatId) throws DoesNotExistException {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Chats WHERE chat_id=?",
                Integer.class, chatId);

        if (count == null || count == 0) {
            throw new DoesNotExistException("Чат не существует");
        }

        jdbcTemplate.update("DELETE FROM Chat_Link_Connection WHERE chat_id=?", chatId);
        jdbcTemplate.update("DELETE FROM Chats WHERE chat_id=?", chatId);
    }

    @Override
    public void makeTrack(long chatId) {
        jdbcTemplate.update("UPDATE Chats SET wait_track=1 WHERE chat_id=?", chatId);
    }

    @Override
    public void makeUntrack(long chatId) {
        jdbcTemplate.update("UPDATE Chats SET wait_untrack=1 WHERE chat_id=?", chatId);
    }

    @Override
    public void deleteTrack(long chatId) {
        jdbcTemplate.update("UPDATE Chats SET wait_track=0 WHERE chat_id=?", chatId);
    }

    @Override
    public void deleteUntrack(long chatId) {
        jdbcTemplate.update("UPDATE Chats SET wait_untrack=0 WHERE chat_id=?", chatId);
    }

    @Override
    public boolean isWaitingTrack(long chatId) {
        Integer isWaiting = jdbcTemplate.queryForObject("SELECT wait_track FROM Chats WHERE "
            + "chat_id = ?", Integer.class, chatId);

        return isWaiting == 1;
    }

    @Override
    public boolean isWaitingUntrack(long chatId) {
        Integer isWaiting = jdbcTemplate.queryForObject("SELECT wait_untrack FROM Chats WHERE "
            + "chat_id=?", Integer.class, chatId);

        return isWaiting == 1;
    }
}
