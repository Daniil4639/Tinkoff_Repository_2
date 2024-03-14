package edu.java.scrapper.db;

import edu.java.scrapper.IntegrationEnvironment;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DataBaseIntegrationTest extends IntegrationEnvironment {

    @Test
    public void dataBaseTest() {
        assertThat(liquibase.getDatabase()).isNotNull();

        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE " +
            "table_name = ?";

        assertThat(template.queryForObject(query, Integer.class, "chats"))
            .isEqualTo(1);

        assertThat(template.queryForObject(query, Integer.class, "links"))
            .isEqualTo(1);

        assertThat(template.queryForObject(query, Integer.class, "chat_link_connection"))
            .isEqualTo(1);
    }
}
