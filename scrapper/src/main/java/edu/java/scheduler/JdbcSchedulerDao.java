package edu.java.scheduler;

import edu.java.response.api.LinkDataBaseInfo;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSchedulerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LinkDataBaseInfo[] getOldLinksRequest(int minutesAgo) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime oldLinksTime = nowTime.minusMinutes(minutesAgo);

        LinkDataBaseInfo[] list =  jdbcTemplate.query(
                "SELECT * FROM Links WHERE last_check <= '" + Timestamp.valueOf(oldLinksTime) + "'",
                (rs, rowNum) -> new LinkDataBaseInfo(rs.getInt("id"),
                    rs.getString("url"),
                    createCorrectDate(rs.getDate("updated_at")), null))
            .toArray(new LinkDataBaseInfo[]{});

        for (LinkDataBaseInfo linkInfo: list) {
            linkInfo.setTgChatIds(jdbcTemplate.query(
                "SELECT * FROM Chat_Link_Connection WHERE "
                    + "link_id=" + linkInfo.getId(), (rs, rowNum) -> rs.getInt("chat_id"))
                .toArray(new Integer[] {}));
        }

        jdbcTemplate.update("UPDATE Links SET last_check = '" + Timestamp.valueOf(nowTime)
            + "'");

        return list;
    }

    private OffsetDateTime createCorrectDate(Date date) {
        return date.toLocalDate().atTime(OffsetTime.now());
    }
}
