package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@SuppressWarnings("ReturnCount")
public class MessageService {

    private final LinkProcessor linkProcessor;
    private final TelegramBot bot;
    private final List<Command> commands;
    private final String unknownCommand = "Команда не распознана!";
    private final String emptyMessage = "Empty message!";

    public void checkUpdate(Update update) {
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

                command.message(update.message().chat().id());
                return;
            }
        }

        linkProcessor.checkLink(update);
    }

    public void sendUpdate(LinkUpdateRequest request) {
        for (int chatId: request.getTgChatIds()) {
            bot.execute(new SendMessage(chatId, request.getDescription()));
        }
    }
}
