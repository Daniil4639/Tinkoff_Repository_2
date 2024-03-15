package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.responses.ListLinksResponse;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    @Autowired
    private ScrapperClient client;
    private long chatId;

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        this.chatId = update.message().chat().id();

        return new SendMessage(update.message().chat().id(), this.message());
    }

    @Override
    public String message() {
        ListLinksResponse list = client.getLinks(chatId).block();
        StringBuilder result = new StringBuilder();

        if (list == null || list.getSize() == 0) {
            result.append("Список отслеживаемых ресурсов пуст!");
        } else {
            result.append("Отслеживаемые ссылки:").append(System.lineSeparator());
            Arrays.stream(list.getLinks()).forEach(response -> {
                result.append(response.getUrl()).append(System.lineSeparator());
            });
        }

        return result.toString();
    }
}
