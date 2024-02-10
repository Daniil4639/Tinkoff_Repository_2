package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class UpdateRunnerBot {

    private final TelegramBot bot;

    public UpdateRunnerBot(String token, AnnotationConfigApplicationContext context) {
        bot = (TelegramBot) context.getBean(
            "getBot", TelegramBot.class, token);
    }

    public void run() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                list.forEach(update -> MessageService.checkUpdate(update, bot));

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
