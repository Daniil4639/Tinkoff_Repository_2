package edu.java.domain;

import edu.java.responses.LinkResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@RequiredArgsConstructor
public class JdbcLinkDao {

    private final JdbcTemplate jdbcTemplate;

    public LinkResponse[] getLinksByChatRequest(int chatId) {
        List<Integer> idList = jdbcTemplate.query("SELECT * FROM Chat_Link_Connection WHERE "
            + "chat_id=?", (rs, rowNum) -> rs.getInt("link_id"), chatId);

        if (idList.isEmpty()) {
            return null;
        }

        String idStr = String.join(",", Collections.nCopies(idList.size(), "?"));

        return jdbcTemplate.query(
            String.format("SELECT * FROM Links WHERE id IN (%s)", idStr),
            idList.toArray(),
            (rs, rowNum) -> new LinkResponse(rs.getInt("id"), rs.getString("url")))
            .toArray(new LinkResponse[]{});
    }

    public void addLinkRequest(int chatId, String link, OffsetDateTime createdDate,
        OffsetDateTime updatedDate) {

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Chats WHERE chat_id=?",
            Integer.class, chatId);

        if (count == null || count == 0) {
            return;
        }

        Timestamp creation = Timestamp.valueOf(createdDate.toLocalDateTime());
        Timestamp lastUpdate = Timestamp.valueOf(updatedDate.toLocalDateTime());
        Timestamp lastCheck = Timestamp.valueOf(LocalDateTime.now());

        jdbcTemplate.update("INSERT INTO Links VALUES (DEFAULT, ?, ?, ?, ?) ON CONFLICT (url) DO NOTHING",
            link, creation, lastUpdate, lastCheck);

        jdbcTemplate.update("INSERT INTO Chat_Link_Connection VALUES ((SELECT chat_id FROM"
            + " Chats WHERE chat_id=?), (SELECT id FROM Links WHERE"
            + " url=?)) ON CONFLICT DO NOTHING", chatId, link);
    }

    public Integer getLinkId(String link) {
        return jdbcTemplate.queryForObject("SELECT id FROM Links WHERE url=?",
            Integer.class, link);
    }

    public void deleteLinkRequest(int chatId, int linkId) {
        jdbcTemplate.update("DELETE FROM Chat_Link_Connection WHERE chat_id=? AND link_id=?",
            chatId, linkId);

        Integer linkCount = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM Chat_Link_Connection "
            + "WHERE link_id=?", Integer.class, linkId);

        if (linkCount == null || linkCount != 0) {
            return;
        }

        jdbcTemplate.update("DELETE FROM Links WHERE id=?", linkId);
    }
}
