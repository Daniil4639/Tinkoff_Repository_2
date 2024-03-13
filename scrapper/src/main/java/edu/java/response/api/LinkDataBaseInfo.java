package edu.java.response.api;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkDataBaseInfo {

    private Integer id;
    private String url;
    private OffsetDateTime lastUpdate;
    private Integer[] tgChatIds;
}
