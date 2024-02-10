package bot.commands;

import edu.java.bot.commands.HelpCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelpCommandTest {

    @Test
    @DisplayName("Проверка вывода списка доступных команд")
    void helpCommandTest() {
        HelpCommand help = new HelpCommand();
        Pattern pattern = Pattern.compile("/[a-zA-Z]* - [а-яА-Я ]*");

        String[] messageLines = help.message().split(System.lineSeparator());
        for (int i = 2; i < messageLines.length; i++) {
            Matcher matcher = pattern.matcher(messageLines[i]);

            assertThat(matcher.find()).isTrue();
        }
    }
}
