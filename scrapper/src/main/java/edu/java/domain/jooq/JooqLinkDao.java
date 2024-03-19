package edu.java.domain.jooq;

import edu.java.response.api.LinkResponse;
import edu.jooq.tables.ChatLinkConnection;
import edu.jooq.tables.Chats;
import edu.jooq.tables.Links;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JooqLinkDao {

    private final DSLContext jooqContext;

    public LinkResponse[] getLinksByChatRequest(long chatId) {
        List<Integer> idList = jooqContext.select(ChatLinkConnection.CHAT_LINK_CONNECTION.LINK_ID)
            .from(ChatLinkConnection.CHAT_LINK_CONNECTION)
            .where(ChatLinkConnection.CHAT_LINK_CONNECTION.CHAT_ID.eq(chatId))
            .fetchInto(Integer.class);

        if (idList.isEmpty()) {
            return null;
        }

        return jooqContext.select(Links.LINKS.ID, Links.LINKS.URL)
            .from(Links.LINKS)
            .where(Links.LINKS.ID.in(idList))
            .fetch(elem -> new LinkResponse(Math.toIntExact(elem.component1()),
                elem.component2()))
            .toArray(new LinkResponse[] {});
    }

    public void addLinkRequest(long chatId, String link, OffsetDateTime createdDate,
        OffsetDateTime updatedDate) {

        int count = jooqContext.selectCount()
            .from(Chats.CHATS)
            .where(Chats.CHATS.CHAT_ID.eq(chatId))
            .execute();

        if (count == 0) {
            return;
        }

        jooqContext.insertInto(Links.LINKS)
            .values(DSL.defaultValue(Links.LINKS.ID), link, updatedDate,
                createdDate, OffsetDateTime.now())
            .execute();

        jooqContext.insertInto(ChatLinkConnection.CHAT_LINK_CONNECTION)
                .values(chatId, jooqContext.select(Links.LINKS.ID)
                    .from(Links.LINKS).where(Links.LINKS.URL.eq(link)))
                    .execute();
    }

    public Long getLinkId(String link) {
        return jooqContext.select(Links.LINKS.ID)
            .from(Links.LINKS)
            .where(Links.LINKS.URL.eq(link))
            .fetch()
            .map(Record1::component1)
            .getFirst();
    }

    public void deleteLinkRequest(long chatId, long linkId) {
        jooqContext.delete(ChatLinkConnection.CHAT_LINK_CONNECTION)
                .where(ChatLinkConnection.CHAT_LINK_CONNECTION.CHAT_ID.eq(chatId)
                    .and(ChatLinkConnection.CHAT_LINK_CONNECTION.LINK_ID.eq(linkId)))
                    .execute();

        int linkCount = jooqContext.fetchCount(jooqContext
            .selectFrom(ChatLinkConnection.CHAT_LINK_CONNECTION)
            .where(ChatLinkConnection.CHAT_LINK_CONNECTION.LINK_ID.eq(linkId)));

        if (linkCount != 0) {
            return;
        }

        jooqContext.delete(Links.LINKS)
                .where(Links.LINKS.ID.eq(linkId))
                    .execute();
    }
}
