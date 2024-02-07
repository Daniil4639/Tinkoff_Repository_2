package bot.controller;

import edu.java.bot.controller.BotUtilities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelpCommandTest {

    @Test
    @DisplayName("Проверка вывода списка доступных команд")
    void helpCommandTest() {
        String result = BotUtilities.help();
        String[] lines = result.split(System.lineSeparator());
        Pattern pattern = Pattern.compile("\\d*\\. /[a-z]* - [а-яА-Я]*");

        for (int ind = 2; ind < lines.length; ind++) {
            Matcher matcher = pattern.matcher(lines[ind]);

            assertThat(matcher.find()).isTrue();
        }
    }
}
