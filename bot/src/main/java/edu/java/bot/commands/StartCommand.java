package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    @Autowired
    private ScrapperClient client;
    private long chatId;

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
        this.chatId = update.message().chat().id();

        return new SendMessage(update.message().chat().id(), this.message());
    }

    @Override
    public String message() {

        return client.registerChat(chatId).block();
    }
}
