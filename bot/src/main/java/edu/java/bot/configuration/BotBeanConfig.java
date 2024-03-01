package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.clients.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotBeanConfig {

    @Bean
    public ScrapperClient getScrapperClient(ApplicationConfig config) {
        return new ScrapperClient(config.scrapperBaseUrl());
    }

    @Bean
    public TelegramBot getBot(ApplicationConfig config) {
        if (config.telegramToken() == null) {
            throw new IllegalArgumentException("Empty Telegram-Token!");
        }
        return new TelegramBot(config.telegramToken());
    }
}
