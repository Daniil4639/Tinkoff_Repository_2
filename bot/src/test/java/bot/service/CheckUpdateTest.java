package bot.service;

import bot.AbstractTest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.lang.reflect.Field;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckUpdateTest extends AbstractTest {

    @ParameterizedTest(name = "#{index} - Run with args = {0}")
    @MethodSource("provideArguments")
    @DisplayName("Проверка работы atBash()")
    void checkUpdateTest(String receivedString, String answer) throws NoSuchFieldException, IllegalAccessException {
        Update update = new Update();
        Message message = new Message();

        Field textField = message.getClass().getDeclaredField("text");
        textField.setAccessible(true);
        textField.set(message, receivedString);

        Field messageField = update.getClass().getDeclaredField("message");
        messageField.setAccessible(true);
        messageField.set(update, message);

        assertThat(messageService.checkUpdate(update, bot)).isEqualTo(answer);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
            Arguments.of("/start", "Пользователь зарегистрирован!"),
            Arguments.of("/track", "Введите ссылку на ресурс, на который хотите подписаться:"),
            Arguments.of("/untrack", "Введите ссылку на ресурс, от которого хотите отписаться:"),
            Arguments.of("/list", "Список отслеживаемых ресурсов пуст!")
        );
    }
}
