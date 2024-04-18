package edu.java.scrapper.kafka;

import edu.java.clients.bot.BotKafkaClient;
import edu.java.requests.LinkUpdateRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.restassured.internal.common.assertion.Assertion;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class KafkaProducerTest {

    @ClassRule
    public static KafkaContainer kafkaContainer;
    public static KafkaConsumer<String, LinkUpdateRequest> consumer;

    @Autowired
    private BotKafkaClient client;

    static {
        kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.4"));
        kafkaContainer.start();

        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            kafkaContainer.getBootstrapServers()
        );
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            "test"
        );
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class
        );
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            JsonDeserializer.class
        );
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("test"));
    }

    @Test
    public void producerTest() {
        client.updateLink("testLink", new int[] {1, 2, 3}, "some-text");

        await().atMost(10, TimeUnit.SECONDS).until(() -> {
                client.updateLink("testLink", new int[] {1, 2, 3}, "some-text");
                ConsumerRecords<String, LinkUpdateRequest> records = consumer.poll(Duration.ofMillis(100));

                if (!records.isEmpty()) {
                    Assertions.assertThat(records.iterator()
                        .next().value().getUrl()).isEqualTo("testLink");
                    return true;
                }
                else {
                    return false;
                }
        });
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("app.useQueue", () -> {return true;});
        registry.add("app.kafka.bootstrap-server", kafkaContainer::getBootstrapServers);
    }
}
