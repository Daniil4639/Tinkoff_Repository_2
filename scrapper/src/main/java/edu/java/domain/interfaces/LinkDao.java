package edu.java.domain.interfaces;

import edu.java.response.api.LinkResponse;
import java.time.OffsetDateTime;

public interface LinkDao {

    LinkResponse[] getLinksByChatRequest(long chatId);

    void addLinkRequest(long chatId, String link, OffsetDateTime createdDate,
        OffsetDateTime updatedDate);

    Long getLinkId(String link);

    void deleteLinkRequest(long chatId, long linkId);
}
