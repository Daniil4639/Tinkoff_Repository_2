package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
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

    private final String unknownCommand = "Команда не распознана!";
    private final List<Command> commands;
    private final String emptyMessage = "Empty message!";

    public String checkUpdate(Update update, TelegramBot bot) {
        if (update.message() == null) {
            log.error(emptyMessage);
            return emptyMessage;
        }

        for (Command command: commands) {
            if (command.name().equals(update.message().text())) {
                try {
                    bot.execute(command.handle(update));
                    log.info("Message has been sent by: " + command.name());
                } catch (Exception exception) {
                    log.error("Incorrect SendMessage handler!", exception);
                }

                return command.message();
            }
        }

        try {
            bot.execute(new SendMessage(update.message().chat().id(), unknownCommand));
            log.info("Command was not recognized!");
        } catch (Exception exception) {
            log.error("Received Update hasn't enough information for sending message!", exception);
        }

        return unknownCommand;
    }
}
