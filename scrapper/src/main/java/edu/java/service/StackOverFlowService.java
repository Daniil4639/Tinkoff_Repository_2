package edu.java.service;

import edu.java.clients.StackOverflowClient;
import edu.java.response.resource.sof.StackOverFlowAnswer;
import edu.java.response.resource.sof.StackOverFlowResponse;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowService {
    private final StackOverflowClient stackOverflowClient;

    public StackOverFlowResponse getStackOverFlowInfo(String id) {
        return stackOverflowClient.getInfo(id);
    }

    public String getUpdateInfo(String id, OffsetDateTime lastUpdateDate,
        StackOverFlowResponse question) {

        List<StackOverFlowAnswer> answers = stackOverflowClient
            .getExtendedInfo(id, lastUpdateDate);

        StringBuilder result = new StringBuilder();
        result.append("Активность в обсуждении вопроса:").append(question.getQuestionName())
            .append(System.lineSeparator())
            .append("(ссылка: ").append(question.getQuestionLink()).append(")")
            .append(System.lineSeparator()).append(System.lineSeparator());

        if (answers == null || answers.isEmpty()) {
            return result.toString();
        }

        for (int answerInd = 0; answerInd < answers.size(); answerInd++) {
            StackOverFlowAnswer answer = answers.get(answerInd);

            result.append("Ответ №").append(answerInd + 1).append(")")
                .append("   Автор: ").append(answer.getOwner().getName())
                .append(System.lineSeparator())
                .append("   Репутация: ").append(answer.getOwner().getRep())
                .append(System.lineSeparator())
                .append("   Время ответа: ").append(answer.getCreationDate())
                .append(System.lineSeparator()).append(System.lineSeparator());
        }

        return result.toString();
    }
}
