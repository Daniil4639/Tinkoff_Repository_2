package edu.java.bot.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import lombok.Getter;

@Getter public class BotConfig {

    private String botName;

    private String botToken;

    public BotConfig() {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("bot/src/main/resources/application.properties"));
            botName = prop.getProperty("bot.name");
            botToken = prop.getProperty("bot.token");
        } catch (IOException e) {
            botName = null;
            botToken = null;
        }
    }
}
