package edu.java.clients;

import edu.java.response.resource.sof.StackOverFlowAnswer;
import edu.java.response.resource.sof.StackOverFlowAnswersItems;
import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.response.resource.sof.StackOverFlowResponseItems;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

public class StackOverflowClient extends Client {

    private final ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

    public StackOverflowClient(String url) {
        super(url);
    }

    public StackOverFlowResponse getInfo(String id) {
        String url = id + "?site=stackoverflow";

        Optional<StackOverFlowResponseItems> responseItems =  client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(StackOverFlowResponseItems.class)
                .log()
                .blockOptional();

        if (responseItems.isEmpty()) {
            return null;
        }

        StackOverFlowResponse response = responseItems.get().getResponseList().getFirst();

        response.setLastUpdate(response.getLastUpdate()
            .toLocalDateTime().atOffset(offset));

        return response;
    }

    public List<StackOverFlowAnswer> getExtendedInfo(String id, OffsetDateTime lastUpdate) {
        List<StackOverFlowAnswer> answers = getAnswers(id);

        answers.forEach(elem -> elem.setCreationDate(
            elem.getCreationDate().toLocalDateTime().atOffset(offset)));

        return answers.stream().filter(elem -> elem.getCreationDate().isAfter(lastUpdate))
            .toList();
    }

    private List<StackOverFlowAnswer> getAnswers(String id) {
        String uri = id + "/answers?site=stackoverflow";

        Optional<StackOverFlowAnswersItems> answersItems = client.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StackOverFlowAnswersItems.class)
            .blockOptional();

        return answersItems.map(StackOverFlowAnswersItems::getAnswers).orElse(null);
    }
}
