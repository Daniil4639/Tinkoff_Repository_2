package bot.service;

import edu.java.bot.commands.Command;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommandsTest {

    @Test
    public void commandsTest() {
        List<? extends Command> commands = MessageService.commands();

        assertThat(commands.stream().map(Command::name).toArray())
            .contains("/start", "/list", "/untrack", "/track", "/help");
    }
}
