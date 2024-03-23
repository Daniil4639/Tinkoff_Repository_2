package edu.java.scheduler.daos;

import edu.java.domain.jpa.entities.ConnectionEntity;
import edu.java.domain.jpa.entities.LinkEntity;
import edu.java.response.api.LinkDataBaseInfo;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaSchedulerDao implements SchedulerDao {

    private final SessionFactory sessionFactory;

    @Override
    public LinkDataBaseInfo[] getOldLinksRequest(OffsetDateTime oldLinksTime) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<LinkEntity> links = session.createQuery("from LinkEntity", LinkEntity.class)
                .getResultList()
                .stream()
                .filter(elem -> oldLinksTime.isAfter(createCorrectDate(elem.getLastCheck())))
                .toList();

            return links.stream()
                .map(elem -> new LinkDataBaseInfo(Math.toIntExact(elem.getId()),
                elem.getUrl(), createCorrectDate(elem.getLastUpdate()), null))
                .toList()
                .toArray(new LinkDataBaseInfo[] {});
        }
    }

    @Override
    public void addTgChatsInfo(LinkDataBaseInfo linkInfo) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Integer[] chats = session.createQuery(
                "from ConnectionEntity where id.link.id=" + linkInfo.getId(),
                ConnectionEntity.class)
                .stream()
                .map(elem -> Math.toIntExact(elem.getId().getChat().getId()))
                .toArray(Integer[]::new);

            linkInfo.setTgChatIds(chats);
        }
    }

    @Override
    public void updateLastCheck(LinkDataBaseInfo[] list, OffsetDateTime nowTime) {
        List<Integer> ids = Arrays.stream(list)
            .map(LinkDataBaseInfo::getId)
            .toList();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (Integer id: ids) {
                var link = session.get(LinkEntity.class, id);
                link.setLastCheck(Timestamp.valueOf(nowTime.toLocalDateTime()));
            }

            session.flush();
        }
    }

    @Override
    public void updateLinkDate(int linkId, OffsetDateTime newLastUpdateDate) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var link = session.get(LinkEntity.class, linkId);
            link.setLastUpdate(Timestamp.valueOf(newLastUpdateDate.toLocalDateTime()));
            session.flush();
        }
    }

    private OffsetDateTime createCorrectDate(Timestamp date) {
        return date.toLocalDateTime().atOffset(ZoneId.systemDefault().getRules()
            .getOffset(Instant.now()));
    }
}
