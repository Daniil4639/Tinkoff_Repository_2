package bot.service;

import bot.AbstractServiceTest;
import edu.java.bot.commands.Command;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommandsTest extends AbstractServiceTest {

    @Test
    public void commandsTest() {
        List<Command> commands = messageService.getCommands();

        assertThat(commands.stream().map(Command::name).toArray())
            .contains("/start", "/list", "/untrack", "/track", "/help");
    }
}
