package edu.java.domain.jpa;

import edu.java.domain.interfaces.LinkDao;
import edu.java.domain.jpa.entities.ChatEntity;
import edu.java.domain.jpa.entities.ConnectionEntity;
import edu.java.domain.jpa.entities.ConnectionIds;
import edu.java.domain.jpa.entities.LinkEntity;
import edu.java.responses.LinkResponse;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaLinkDao implements LinkDao {

    private final SessionFactory sessionFactory;

    @Override
    public LinkResponse[] getLinksByChatRequest(long chatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query<ConnectionEntity> query = session.createQuery("from ConnectionEntity where id.chat.id=:id",
                ConnectionEntity.class);
            query.setParameter("id", chatId);

            List<ConnectionEntity> idList = query.getResultList();

            if (idList.isEmpty()) {
                return null;
            }

            LinkResponse[] responses = new LinkResponse[idList.size()];
            for (int index = 0; index < idList.size(); index++) {
                var link = session.get(LinkEntity.class, idList.get(index).getId().getLink().getId());

                responses[index] = new LinkResponse(Math.toIntExact(link.getId()), link.getUrl());
            }

            transaction.commit();
            return responses;
        }
    }

    @Override
    public void addLinkRequest(long chatId, String link, OffsetDateTime createdDate, OffsetDateTime updatedDate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            ChatEntity chat = session.get(ChatEntity.class, chatId);

            if (chat == null) {
                return;
            }

            LinkEntity newLink = new LinkEntity(null, link,
                Timestamp.valueOf(updatedDate.toLocalDateTime()),
                Timestamp.valueOf(createdDate.toLocalDateTime()),
                getCurrTime());

            session.persist(newLink);
            session.persist(new ConnectionEntity(new ConnectionIds(chat, newLink)));

            session.flush();
            transaction.commit();
        }
    }

    @Override
    public Long getLinkId(String link) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<LinkEntity> query = session.createQuery("from LinkEntity where url=':link'",
                LinkEntity.class);
            query.setParameter("link", link);

            return query.getResultList()
                .getFirst()
                .getId();
        }
    }

    @Override
    public void deleteLinkRequest(long chatId, long linkId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query<ConnectionEntity> query = session.createQuery("from ConnectionEntity where id.link.id=:linkId",
                ConnectionEntity.class);
            query.setParameter("linkId", linkId);

            List<ConnectionEntity> connections = query.getResultList();

            boolean multiDependence = (connections.size() > 1);

            for (ConnectionEntity connection: connections) {
                if (connection.getId().getChat().getId() == chatId) {
                    session.remove(connection);
                }
            }

            if (!multiDependence) {
                session.remove(session.get(LinkEntity.class, linkId));
            }

            session.flush();
            transaction.commit();
        }
    }

    private Timestamp getCurrTime() {
        return Timestamp.valueOf(OffsetDateTime.now().toLocalDateTime());
    }
}
