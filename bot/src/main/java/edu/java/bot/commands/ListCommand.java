package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import edu.java.responses.LinkResponseList;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {

    private final ScrapperClient client;

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
        return new SendMessage(update.message().chat().id(),
            this.message(update.message().chat().id()));
    }

    @Override
    public String message(long chatId) {
        LinkResponseList list = client.getLinks(chatId).block();
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
