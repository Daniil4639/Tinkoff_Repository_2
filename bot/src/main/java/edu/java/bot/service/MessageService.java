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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class MessageService {

    private final List<Command> commands;

    public String checkUpdate(Update update, TelegramBot bot) {
        if (update.message() == null) {
            return null;
        }

        for (Command command: commands) {
            if (command.name().equals(update.message().text())) {
                try {
                    bot.execute(command.handle(update));
                } catch (Exception exception) {
                    log.error("Incorrect SendMessage handler!", exception);
                }

                return command.message();
            }
        }

        String UNKNOWN_COMMAND = "Команда не распознана!";
        try {
            bot.execute(new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND));
        } catch (Exception exception) {
            log.error("Received Update hasn't enough information for sending message!", exception);
        }

        return UNKNOWN_COMMAND;
    }
}
