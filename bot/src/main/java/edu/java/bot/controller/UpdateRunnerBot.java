package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class UpdateRunnerBot {

    private final Logger logger = LogManager.getLogger();
    private final TelegramBot bot;

    public UpdateRunnerBot(TelegramBot bot) {
        this.bot = bot;
        this.run();
    }

    public void run() {
        logger.info("Telegram-Bot is running!");

        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                list.forEach(update -> MessageService.checkUpdate(update, bot));

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
