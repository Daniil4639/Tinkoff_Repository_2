package edu.java.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkUpdateRequest {

    private Integer id;
    private String url;
    private String description;
    private int[] tgChatIds;
}
