package edu.java.scrapper.db;

import edu.java.scrapper.IntegrationEnvironment;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DataBaseIntegrationTest extends IntegrationEnvironment {

    @Test
    public void dataBaseTest() {
        assertThat(liquibase.getDatabase()).isNotNull();
    }
}
