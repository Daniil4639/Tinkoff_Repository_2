package bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.BotApplication;
import edu.java.bot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = {BotApplication.class})
public class AbstractTest {

    @Autowired
    protected MessageService messageService;

    @MockBean
    protected TelegramBot bot;
}
