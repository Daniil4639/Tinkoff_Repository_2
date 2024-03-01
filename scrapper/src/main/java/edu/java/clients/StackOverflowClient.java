package edu.java.clients;

import edu.java.response.resource.StackOverFlowResponse;
import edu.java.response.resource.StackOverFlowResponseItems;
import java.util.Objects;
import org.springframework.http.MediaType;

public class StackOverflowClient extends Client {

    public StackOverflowClient(String url) {
        super(url);
    }

    public StackOverFlowResponse getInfo(String id) {
        String url = id + "?site=stackoverflow";

        return Objects.requireNonNull(client.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StackOverFlowResponseItems.class)
            .log()
            .block())
            .getResponseList()
            .getFirst();
    }
}
