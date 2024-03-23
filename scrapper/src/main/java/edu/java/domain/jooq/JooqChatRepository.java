package edu.java.domain.jooq;

import edu.java.api_exceptions.ChatAlreadyExistsException;
import edu.java.api_exceptions.DoesNotExistException;
import edu.java.domain.interfaces.ChatRepository;
import edu.jooq.tables.ChatLinkConnection;
import edu.jooq.tables.Chats;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {

    private final DSLContext jooqContext;

    @Override
    public void addChatRequest(long chatId) throws ChatAlreadyExistsException  {
        try {
            jooqContext.insertInto(Chats.CHATS)
                .values(chatId, 0, 0, Timestamp.valueOf(LocalDateTime.now()))
                .execute();
        } catch (Exception ex) {
            throw new ChatAlreadyExistsException("Чат уже зарегистрирован");
        }
    }

    @Override
    public void deleteChatRequest(long chatId) throws DoesNotExistException {
        int count = jooqContext.fetchCount(jooqContext
            .selectFrom(Chats.CHATS)
            .where(Chats.CHATS.CHAT_ID.eq(chatId)));

        if (count == 0) {
            throw new DoesNotExistException("Чат не существует");
        }

        jooqContext.delete(ChatLinkConnection.CHAT_LINK_CONNECTION)
                .where(ChatLinkConnection.CHAT_LINK_CONNECTION.CHAT_ID.eq(chatId))
                    .execute();
        jooqContext.delete(Chats.CHATS)
                .where(Chats.CHATS.CHAT_ID.eq(chatId))
                    .execute();
    }

    @Override
    public void makeTrack(long chatId) {
        jooqContext.update(Chats.CHATS).set(Chats.CHATS.WAIT_TRACK, 1)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    public void makeUntrack(long chatId) {
        jooqContext.update(Chats.CHATS).set(Chats.CHATS.WAIT_UNTRACK, 1)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    public void deleteTrack(long chatId) {
        jooqContext.update(Chats.CHATS).set(Chats.CHATS.WAIT_TRACK, 0)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    public void deleteUntrack(long chatId) {
        jooqContext.update(Chats.CHATS).set(Chats.CHATS.WAIT_UNTRACK, 0)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    public boolean isWaitingTrack(long chatId) {
        long isWaiting = jooqContext.select(Chats.CHATS.WAIT_TRACK)
            .from(Chats.CHATS)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .fetch()
            .map(Record1::component1)
            .getFirst();

        return isWaiting != 0;
    }

    @Override
    public boolean isWaitingUntrack(long chatId) {
        long isWaiting = jooqContext.select(Chats.CHATS.WAIT_UNTRACK)
            .from(Chats.CHATS)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .fetch()
            .map(Record1::component1)
            .getFirst();

        return isWaiting != 0;
    }
}
