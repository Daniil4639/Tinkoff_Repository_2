package bot.configuration;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.BotBeanConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApplicationConfigTest {

    @Test
    public void applicationConfigTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BotBeanConfig.class);
        ApplicationConfig config = context.getBean("getConfig", ApplicationConfig.class);

        assertThat(config.getTelegramToken()).isNotNull();
    }
}
