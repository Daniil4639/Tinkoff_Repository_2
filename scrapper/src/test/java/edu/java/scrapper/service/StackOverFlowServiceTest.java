package edu.java.scrapper.service;

import edu.java.response.resource.sof.StackOverFlowResponse;
import edu.java.scrapper.AbstractClientTest;
import edu.java.service.StackOverFlowService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StackOverFlowServiceTest extends AbstractClientTest {

    private final static OffsetDateTime time = OffsetDateTime.now();

    private final static String STACK_OVER_FLOW_JSON = """
        {
            "items": [
                {
                "title": "test_question",
                "link": "https://stackoverflow.com/questions/2024/test_question",
                "creation_date": 1521703982,
                "last_activity_date": 1574334198
                }
            ]
        }
        """;

    private final static String ANSWERS_RESPONSE = String.format("""
        {
            "items": [
                {
                    "owner": {
                       "reputation": 119,
                       "display_name": "Test_user"
                    },
                    "creation_date": "%s"
                }
            ]
        }
        """, time.toString());

    private final static String CORRECT_RESPONSE = String.format("""
        Активность в обсуждении вопроса:test_name
        (ссылка: test_link)

        Ответ №1)   Автор: Test_user
           Репутация: 119
           Время ответа: %s

        """, time.minusHours(3).toString());

    @Autowired
    private StackOverFlowService stackOverFlowService;

    @Test
    public void stackOverFlowServiceTest() {
        stubFor(get(urlEqualTo("/2024?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(STACK_OVER_FLOW_JSON)));

        StackOverFlowResponse response = stackOverFlowService.getStackOverFlowInfo("2024");

        assertThat(response.getQuestionName()).isEqualTo("test_question");
        assertThat(response.getQuestionLink()).isEqualTo("https://stackoverflow.com/questions/2024/test_question");
        assertThat(response.getCreationDate().toString()).isEqualTo("2018-03-22T07:33:02Z");
        assertThat(response.getLastUpdate().toString()).isEqualTo("2019-11-21T11:03:18+03:00");
    }

    @Test
    public void stackOverFlowExtendedTest() {
        stubFor(get(urlEqualTo("/2024/answers?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withHeader("Content-Type", "application/json")
                .withBody(ANSWERS_RESPONSE)));

        String result = stackOverFlowService.getUpdateInfo("2024", time.minusDays(3),
            new StackOverFlowResponse("test_name", "test_link", OffsetDateTime.now(),
                OffsetDateTime.now()));

        assertThat(result.replace(System.lineSeparator(), "\n"))
            .isEqualTo(CORRECT_RESPONSE);
    }
}
