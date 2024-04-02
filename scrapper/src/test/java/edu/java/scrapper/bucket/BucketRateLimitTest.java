package edu.java.scrapper.bucket;

import edu.java.jdbc.JdbcChatService;
import edu.java.scrapper.AbstractClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DirtiesContext
@AutoConfigureMockMvc
public class BucketRateLimitTest extends AbstractClientTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JdbcChatService service;

    @Value("${bucket4j.filters[0].rate-limits[0].bandwidths[0].capacity}")
    private int limit;

    @Test
    public void rateLimitTest() throws Exception {
        Mockito.doReturn("Чат зарегистрирован").when(service).addChat(12L);

        String request = "/tg-chat/12";

        for (int i = 0; i < limit; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        mockMvc.perform(MockMvcRequestBuilders.get(request))
            .andExpect(MockMvcResultMatchers.status().isTooManyRequests());
    }
}
