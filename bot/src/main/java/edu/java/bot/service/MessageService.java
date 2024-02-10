package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final String UNKNOWN_COMMAND = "Команда не распознана!";

    private MessageService() {}

    public static String checkUpdate(Update update, TelegramBot bot) {
        if (update.message() == null) {
            return null;
        } else {
            List<? extends Command> commands = MessageService.commands();

            for (Command command: commands) {
                if (command.name().equals(update.message().text())) {
                    try {
                        bot.execute(command.handle(update));
                    } catch (Exception ignored) {
                    }

                    return command.message();
                }
            }
        }

        try {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        } catch (Exception ignored) {
        }

        return UNKNOWN_COMMAND;
    }

    public static List<? extends Command> commands() {
        Reflections reflections = new Reflections("edu.java.bot.commands");

        return reflections.getSubTypesOf(Command.class).stream().map(elem -> {
            try {
                return elem.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
