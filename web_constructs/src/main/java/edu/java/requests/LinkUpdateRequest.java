package edu.java.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LinkUpdateRequest {

    private Integer id;
    private String url;
    private String description;
    private int[] tgChatIds;
}
