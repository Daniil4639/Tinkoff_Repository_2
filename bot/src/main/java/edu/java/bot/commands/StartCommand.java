package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final ScrapperClient client;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
       return new SendMessage(update.message().chat().id(),
           this.message(update.message().chat().id()));
    }

    @Override
    public String message(long chatId) {
        try {
            return client.registerChat(chatId).block();
        } catch (Exception ex) {
            return getMessageFromException(ex);
        }
    }
}
