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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaChatRepository implements ChatRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void addChatRequest(long chatId) throws ChatAlreadyExistsException {
        try {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.persist(new ChatEntity(chatId,
                    Timestamp.valueOf(OffsetDateTime.now().toLocalDateTime()), 0, 0));
                session.flush();
            }
        } catch (Exception ex) {
            throw new ChatAlreadyExistsException("Чат уже зарегистрирован");
        }
    }

    @Override
    public void deleteChatRequest(long chatId) throws DoesNotExistException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            session.remove(chat);
            session.flush();
        }
    }

    @Override
    public void makeTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitTrack(1);
            session.flush();
        }
    }

    @Override
    public void makeUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitUntrack(1);
            session.flush();
        }
    }

    @Override
    public void deleteTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitTrack(0);
            session.flush();
        }
    }

    @Override
    public void deleteUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);
            chat.setWaitUntrack(0);
            session.flush();
        }
    }

    @Override
    public boolean isWaitingTrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);

            return chat.getWaitTrack() == 1;
        }
    }

    @Override
    public boolean isWaitingUntrack(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var chat = session.get(ChatEntity.class, chatId);

            return chat.getWaitUntrack() == 1;
        }
    }
}
