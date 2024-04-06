package edu.java.scheduler.daos;

import edu.java.responses.LinkDataBaseInfo;
import edu.jooq.tables.ChatLinkConnection;
import edu.jooq.tables.Links;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JooqSchedulerDao implements SchedulerDao {

    private final DSLContext jooqContext;

    @Override
    public LinkDataBaseInfo[] getOldLinksRequest(OffsetDateTime oldLinksTime) {
        return jooqContext.select(Links.LINKS.ID, Links.LINKS.URL, Links.LINKS.UPDATED_AT)
            .from(Links.LINKS)
            .where(Links.LINKS.LAST_CHECK.lessOrEqual(oldLinksTime))
            .fetch(elem -> new LinkDataBaseInfo(Math.toIntExact(elem.component1()),
                elem.component2(), elem.component3(), null))
            .toArray(new LinkDataBaseInfo[] {});
    }

    @Override
    public void addTgChatsInfo(LinkDataBaseInfo linkInfo) {
        linkInfo.setTgChatIds(jooqContext.select(ChatLinkConnection.CHAT_LINK_CONNECTION.CHAT_ID)
            .from(ChatLinkConnection.CHAT_LINK_CONNECTION)
            .where(ChatLinkConnection.CHAT_LINK_CONNECTION.LINK_ID.eq(Long.valueOf(linkInfo.getId())))
            .fetch(elem -> Math.toIntExact(elem.component1()))
            .toArray(new Integer[] {}));
    }

    @Override
    public void updateLastCheck(LinkDataBaseInfo[] list, OffsetDateTime nowTime) {
        List<Integer> ids = Arrays.stream(list)
            .map(LinkDataBaseInfo::getId)
                .toList();

        jooqContext.update(Links.LINKS)
            .set(Links.LINKS.LAST_CHECK, nowTime)
            .where(Links.LINKS.ID.in(ids))
            .execute();
    }

    @Override
    public void updateLinkDate(int linkId, OffsetDateTime newLastUpdateDate) {
        jooqContext.update(Links.LINKS)
            .set(Links.LINKS.UPDATED_AT, newLastUpdateDate)
            .where(Links.LINKS.ID.eq((long) linkId))
            .execute();
    }
}
