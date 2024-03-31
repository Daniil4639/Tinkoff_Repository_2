package edu.java.scheduler.daos;

import edu.java.responses.LinkDataBaseInfo;
import java.time.OffsetDateTime;

public interface SchedulerDao {

    LinkDataBaseInfo[] getOldLinksRequest(OffsetDateTime oldLinksTime);

    void addTgChatsInfo(LinkDataBaseInfo linkInfo);

    void updateLastCheck(LinkDataBaseInfo[] list, OffsetDateTime nowTime);

    void updateLinkDate(int linkId, OffsetDateTime newLastUpdateDate);
}
