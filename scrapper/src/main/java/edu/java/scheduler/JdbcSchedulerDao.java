package edu.java.scheduler;

import edu.java.responses.LinkDataBaseInfo;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcSchedulerDao {

    private final JdbcTemplate jdbcTemplate;

    public LinkDataBaseInfo[] getOldLinksRequest(OffsetDateTime oldLinksTime) {
        return jdbcTemplate.query(
                "SELECT * FROM Links WHERE last_check <= ?",
                (rs, rowNum) -> new LinkDataBaseInfo(rs.getInt("id"),
                    rs.getString("url"),
                    createCorrectDate(rs.getTimestamp("updated_at")), null),
                Timestamp.valueOf(oldLinksTime.toLocalDateTime()))
            .toArray(new LinkDataBaseInfo[]{});
    }

    public void addTgChatsInfo(LinkDataBaseInfo linkInfo) {
        linkInfo.setTgChatIds(jdbcTemplate.query(
                "SELECT * FROM Chat_Link_Connection WHERE link_id=?",
                (rs, rowNum) -> rs.getInt("chat_id"), linkInfo.getId())
            .toArray(new Integer[] {}));
    }

    public void updateLastCheck(LinkDataBaseInfo[] list, OffsetDateTime nowTime) {
        StringBuilder idStr = new StringBuilder();
        for (int elem: Arrays.stream(list)
            .mapToInt(LinkDataBaseInfo::getId).toArray()) {

            idStr.append(elem).append(", ");
        }

        jdbcTemplate.update(String.format("UPDATE Links SET last_check=? WHERE id IN (%s)",
                idStr.delete(idStr.length() - 2, idStr.length() - 1)),
            Timestamp.valueOf(nowTime.toLocalDateTime()));
    }

    public void updateLinkDate(int linkId, OffsetDateTime newLastUpdateDate) {
        jdbcTemplate.update("UPDATE Links SET updated_at='"
            + Timestamp.valueOf(newLastUpdateDate.toLocalDateTime()) + "' WHERE id=" + linkId);
    }

    private OffsetDateTime createCorrectDate(Timestamp date) {
        return date.toLocalDateTime().atOffset(ZoneId.systemDefault().getRules()
            .getOffset(Instant.now()));
    }
}
