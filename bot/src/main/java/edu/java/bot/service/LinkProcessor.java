package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkProcessor {

    private final ScrapperClient client;
    private final TelegramBot bot;

    public void checkLink(Update update) {
        long id = update.message().chat().id();

        String resultMessage;

        try {
            if (Boolean.TRUE.equals(client.checkTrack(id).block())) {
                client.addLink(update.message().text(), id).block();
                resultMessage = "Ресурс добавлен!";
            } else if (Boolean.TRUE.equals(client.checkUntrack(id).block())) {
                client.deleteLink(update.message().text(), id).block();
                resultMessage = "Ресурс удален!";
            } else {
                resultMessage = "Сообщение не распознано!";
            }
        } catch (Exception ex) {
            resultMessage = ex.getMessage();
        }

        bot.execute(new SendMessage(id, resultMessage));
        this.clear(id);
    }

    public void clear(long chatId) {
        client.deleteTrack(chatId);
        client.deleteUntrack(chatId);
    }
}
