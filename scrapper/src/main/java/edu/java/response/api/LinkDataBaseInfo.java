package edu.java.response.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LinkDataBaseInfo {

    private Integer id;
    private String url;
    private OffsetDateTime lastUpdate;
    private Integer[] tgChatIds;
}
