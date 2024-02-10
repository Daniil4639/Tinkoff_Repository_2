package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.MessageService;
import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), this.message());
    }

    @Override
    public String message() {
        List<? extends Command> commands = MessageService.commands();
        StringBuilder string = new StringBuilder("Поведение бота контролируется следующими командами:");
        string.append(System.lineSeparator()).append(System.lineSeparator());

        commands.forEach(elem -> string.append(elem.name()).append(" - ").append(elem.description())
            .append(System.lineSeparator()));

        return string.toString();
    }
}
