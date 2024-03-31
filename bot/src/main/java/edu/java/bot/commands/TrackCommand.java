package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {

    private final ScrapperClient client;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        client.makeTrack(update.message().chat().id());
        return new SendMessage(update.message().chat().id(),
            this.message(update.message().chat().id()));
    }

    @Override
    public String message(long chatId) {
        return "Введите ссылку на ресурс, на который хотите подписаться:";
    }
}
