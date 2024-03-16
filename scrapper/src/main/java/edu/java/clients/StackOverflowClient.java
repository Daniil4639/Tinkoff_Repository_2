package edu.java.clients;

import edu.java.response.resource.sof.StackOverFlowAnswer;
import edu.java.response.resource.sof.StackOverFlowAnswersItems;
import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.response.resource.sof.StackOverFlowResponseItems;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import org.springframework.http.MediaType;

public class StackOverflowClient extends Client {

    public StackOverflowClient(String url) {
        super(url);
    }

    public StackOverFlowResponse getInfo(String id) {
        String url = id + "?site=stackoverflow";

        StackOverFlowResponse response =  Objects.requireNonNull(client.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StackOverFlowResponseItems.class)
            .log()
            .block())
            .getResponseList()
            .getFirst();

        response.setLastUpdate(response.getLastUpdate()
            .toLocalDateTime().atOffset(ZoneId.systemDefault().getRules()
            .getOffset(Instant.now())));

        return response;
    }

    public List<StackOverFlowAnswer> getExtendedInfo(String id, OffsetDateTime lastUpdate) {
        List<StackOverFlowAnswer> answers = getAnswers(id);

        return answers.stream().peek(elem -> elem.setCreationDate(
            elem.getCreationDate().toLocalDateTime().atOffset(ZoneId.systemDefault().getRules()
                .getOffset(Instant.now()))
        )).filter(elem -> elem.getCreationDate().isAfter(lastUpdate))
            .toList();
    }

    private List<StackOverFlowAnswer> getAnswers(String id) {
        String uri = id + "/answers?site=stackoverflow";

        return Objects.requireNonNull(client.get()
                        .uri(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(StackOverFlowAnswersItems.class)
                        .block())
            .getAnswers();
    }
}
