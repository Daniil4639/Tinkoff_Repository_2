package edu.java.bot.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/* Request example
    {
    "id": 2,
    "url": "smth",
    "description": "smth",
    "tgChatIds": [2,4,5]
    }
*/

@Getter
@Setter
@RequiredArgsConstructor
public class LinkUpdateRequest {

    private Integer id;
    private String url;
    private String description;
    private int[] tgChatIds;
}
