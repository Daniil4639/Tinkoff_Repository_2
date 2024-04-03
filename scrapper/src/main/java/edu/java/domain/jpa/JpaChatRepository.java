package edu.java.domain.jpa;

import edu.java.domain.interfaces.ChatRepository;
import edu.java.domain.jpa.entities.ChatEntity;
import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.exceptions.DoesNotExistException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaChatRepository implements ChatRepository {

    private final SessionFactory sessionFactory;
    private static final int IS_WAITING = 1;
    private static final int IS_NOT_WAITING = 0;

    @Override
    public void addChatRequest(long chatId) throws ChatAlreadyExistsException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new ChatEntity(chatId,
                getCurrTime(), IS_NOT_WAITING, IS_NOT_WAITING
            ));
            session.flush();
            transaction.commit();
        } catch (Exception ex) {
            throw new ChatAlreadyExistsException("Чат уже зарегистрирован");
        }
    }

    @Override
    public void deleteChatRequest(long chatId) throws DoesNotExistException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            session.remove(chat);
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public void makeTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitTrack(IS_WAITING);
            session.flush();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void makeUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitUntrack(IS_WAITING);
            session.flush();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void deleteTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitTrack(IS_NOT_WAITING);
            session.flush();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void deleteUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitUntrack(IS_NOT_WAITING);
            session.flush();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean isWaitingTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);

            return chat.getWaitTrack() == IS_WAITING;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean isWaitingUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);

            return chat.getWaitUntrack() == IS_WAITING;
        } catch (Exception ex) {
            return false;
        }
    }

    private Timestamp getCurrTime() {
        return Timestamp.valueOf(OffsetDateTime.now().toLocalDateTime());
    }
}
