package bot.service;

import bot.AbstractServiceTest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckUpdateTest extends AbstractServiceTest {

    @ParameterizedTest(name = "#{index} - Run with args = {0}")
    @MethodSource("provideArguments")
    @DisplayName("Проверка работы atBash()")
    void checkUpdateTest(String receivedString, String answer) throws NoSuchFieldException, IllegalAccessException {
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);

        when(chat.id()).thenReturn(1L);
        when(message.text()).thenReturn(receivedString);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

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
