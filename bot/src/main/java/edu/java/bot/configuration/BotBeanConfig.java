package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotBeanConfig {

    private final Logger logger = LogManager.getLogger();

    @Bean
    public TelegramBot getBot(ApplicationConfig config) {
        if (config.telegramToken() == null) {
            logger.info("Empty Telegram-Token!");
        }
        return new TelegramBot(config.telegramToken());
    }
}
