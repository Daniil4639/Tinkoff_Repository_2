package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BotUtilities {

    private BotUtilities() {}

    public static String start() {
        return "Пользователь зарегистрирован!";
    }

    public static String help() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
            new FileReader("bot/src/main/resources/bot_commans.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException ignored) {
        }

        return sb.toString();
    }

    public static String track() {
        return "Введите ссылку на ресурс, на который хотите подписаться:";
    }

    public static String untrack() {
        return "Введите ссылку на ресурс, от которого хотите отписаться:";
    }

    public static String list() {
        return "Список отслеживаемых ресурсов пуст!";
    }

    public static void printMessage(TelegramBot bot, String text, Long id) {
        SendResponse response = bot.execute(new SendMessage(id, text));
    }
}
