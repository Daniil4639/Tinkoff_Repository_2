package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.requests.LinkUpdateRequest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@SuppressWarnings("ReturnCount")
public class MessageService {

    @Autowired
    private LinkProcessor linkProcessor;
    @Autowired
    private TelegramBot bot;

    private final String unknownCommand = "Команда не распознана!";
    private final List<Command> commands;
    private final String emptyMessage = "Empty message!";

    public void checkUpdate(Update update) {
        if (update.message() == null) {
            log.error(emptyMessage);
            return;
        }

        for (Command command: commands) {
            if (command.name().equals(update.message().text())) {
                linkProcessor.clear(update.message().chat().id());

                try {
                    bot.execute(command.handle(update));
                    log.info("Message has been sent by: " + command.name());
                } catch (Exception exception) {
                    log.error("Incorrect SendMessage handler!", exception);
                }

                command.message();
                return;
            }
        }

        if (linkProcessor.checkLink(update)) {
            return;
        }

        try {
            bot.execute(new SendMessage(update.message().chat().id(), unknownCommand));
            log.info("Command was not recognized!");
        } catch (Exception exception) {
            log.error("Received Update hasn't enough information for sending message!", exception);
        }
    }

    public void sendUpdate(LinkUpdateRequest request) {
        for (int chatId: request.getTgChatIds()) {
            bot.execute(new SendMessage(chatId, request.getDescription()));
        }
    }
}
