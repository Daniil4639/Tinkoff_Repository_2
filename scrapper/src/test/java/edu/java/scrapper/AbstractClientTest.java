package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AbstractClientTest {

    private final static int PORT = 1234;
    private WireMockServer wireMockServer;

    @Before
    public void startServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(PORT));
        wireMockServer.start();
        WireMock.configureFor("localhost", PORT);
    }

    @After
    public void stopServer() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }
}
