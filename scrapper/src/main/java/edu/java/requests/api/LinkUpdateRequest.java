package edu.java.requests.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
public class LinkUpdateRequest {

    private Integer id;
    private String url;
    private String description;
    private int[] tgChatIds;
}
