package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class UpdateRunnerBot {

    private final Map<String, Supplier<String>> commands = Map.ofEntries(
        Map.entry("/start", BotUtilities::start),
        Map.entry("/help", BotUtilities::help),
        Map.entry("/track", BotUtilities::track),
        Map.entry("/untrack", BotUtilities::untrack),
        Map.entry("/list", BotUtilities::list)
    );

    private final TelegramBot bot;

    public UpdateRunnerBot() {
        BotConfig config = new BotConfig();
        bot = new TelegramBot(config.getBotToken());
    }

    public void run() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                list.forEach(UpdateRunnerBot.this::checkUpdate);
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    private void checkUpdate(Update update) {
        Message message = update.message();


        if (message != null && commands.containsKey(message.text())) {
            String answer = commands.get(message.text()).get();

            BotUtilities.printMessage(bot, answer, message.chat().id());

        } else if (message != null) {
            bot.execute(new SendMessage(message.chat().id(), "Команда не распознана!"));
        }
    }
}
