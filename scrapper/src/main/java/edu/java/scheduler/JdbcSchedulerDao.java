package edu.java.scheduler;

import edu.java.responses.LinkDataBaseInfo;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcSchedulerDao {

    private final JdbcTemplate jdbcTemplate;

    public LinkDataBaseInfo[] getOldLinksRequest(LocalDateTime oldLinksTime) {
        return jdbcTemplate.query(
                "SELECT * FROM Links WHERE last_check <= ?",
                (rs, rowNum) -> new LinkDataBaseInfo(rs.getInt("id"),
                    rs.getString("url"),
                    createCorrectDate(rs.getDate("updated_at")), null),
                Timestamp.valueOf(oldLinksTime))
            .toArray(new LinkDataBaseInfo[]{});
    }

    public void addTgChatsInfo(LinkDataBaseInfo linkInfo) {
        linkInfo.setTgChatIds(jdbcTemplate.query(
                "SELECT * FROM Chat_Link_Connection WHERE link_id=?",
                (rs, rowNum) -> rs.getInt("chat_id"), linkInfo.getId())
            .toArray(new Integer[] {}));
    }

    public void updateLinks(LinkDataBaseInfo[] list, LocalDateTime nowTime, String ids) {
        jdbcTemplate.update(String.format("UPDATE Links SET last_check=? WHERE id IN (%s)", ids),
            Timestamp.valueOf(nowTime));
    }

    private OffsetDateTime createCorrectDate(Date date) {
        return date.toLocalDate().atTime(OffsetTime.now());
    }
}
