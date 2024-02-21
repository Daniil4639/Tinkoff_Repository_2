package bot.service;

import bot.AbstractTest;
import edu.java.bot.commands.Command;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommandsTest extends AbstractTest {

    @Test
    public void commandsTest() {
        List<Command> commands = messageService.getCommands();

        assertThat(commands.stream().map(Command::name).toArray())
            .contains("/start", "/list", "/untrack", "/track", "/help");
    }
}
