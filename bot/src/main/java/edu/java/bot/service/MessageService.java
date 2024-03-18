package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
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

    private final LinkProcessor linkProcessor;
    private final List<Command> commands;
    private final String unknownCommand = "Команда не распознана!";
    private final String emptyMessage = "Empty message!";

    public void checkUpdate(Update update, TelegramBot bot) {
        if (update.message() == null) {
            log.error(emptyMessage);
            return;
        }

        boolean isCommand = false;

        for (Command command: commands) {
            if (command.name().equals(update.message().text())) {
                linkProcessor.clear(update.message().chat().id());

                try {
                    bot.execute(command.handle(update));
                    log.info("Message has been sent by: " + command.name());
                } catch (Exception exception) {
                    log.error("Incorrect SendMessage handler!", exception);
                }

                isCommand = true;
                break;
            }
        }

        if (!isCommand) {
            linkProcessor.checkLink(update);
        }
    }
}
