package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UNKNOWN_COMMAND = "Команда не распознана!";

    private MessageService() {}

    public static String checkUpdate(Update update, TelegramBot bot) {
        if (update.message() == null) {
            return null;
        } else {

            for (Command command: MessageService.commands()) {
                if (command.name().equals(update.message().text())) {
                    try {
                        bot.execute(command.handle(update));
                    } catch (Exception exception) {
                        LOGGER.info("Incorrect SendMessage handler!");
                    }

                    return command.message();
                }
            }
        }

        try {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        } catch (Exception exception) {
            LOGGER.info("Received Update hasn't enough information for sending message!");
        }

        return UNKNOWN_COMMAND;
    }

    public static List<? extends Command> commands() {
        return Arrays.asList(
            new StartCommand(),
            new HelpCommand(),
            new TrackCommand(),
            new UntrackCommand(),
            new ListCommand()
        );
    }
}
