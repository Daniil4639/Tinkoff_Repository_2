package bot;

import edu.java.bot.BotApplication;
import edu.java.bot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BotApplication.class})
public class AbstractTest {

    @Autowired
    protected MessageService messageService;
}
