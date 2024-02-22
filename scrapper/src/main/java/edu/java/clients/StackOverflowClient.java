package edu.java.clients;

import edu.java.response.StackOverFlowResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class StackOverflowClient extends Client {

    public StackOverflowClient(String url) {
        super(url);
    }

    public StackOverFlowResponse getStackOverFlowInfo(String url) {
        Mono<ResponseArray> response = client.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ResponseArray.class).log();

        return response.block().getItems()[0];
    }

    @Getter
    @Setter
    private static class ResponseArray {
        private StackOverFlowResponse[] items;
    }
}
