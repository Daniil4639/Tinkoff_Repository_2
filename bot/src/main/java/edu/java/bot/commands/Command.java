package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {

    String name();

    String description();

    SendMessage handle(Update update);

    String message(long chatId);

    default String getMessageFromException(Exception ex) {
        String message = ex.getMessage();
        int messageInd = message.indexOf(":");

        return message.substring(messageInd + 1);
    }
}
