package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class UpdateRunnerBot {

    private final TelegramBot bot;
    private final MessageService messageService;

    public UpdateRunnerBot(TelegramBot bot, MessageService messageService) {
        this.bot = bot;
        this.messageService = messageService;
        this.run();
    }

    public void run() {
        log.info("Telegram-Bot is running!");

        bot.setUpdatesListener(list -> {
            list.forEach(update -> messageService.checkUpdate(update, bot));

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
