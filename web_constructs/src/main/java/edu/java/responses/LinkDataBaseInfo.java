package edu.java.responses;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LinkDataBaseInfo {

    private Integer id;
    private String url;
    private OffsetDateTime lastUpdate;
    private Integer[] tgChatIds;
}
