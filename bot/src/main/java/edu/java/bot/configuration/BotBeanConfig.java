package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BotBeanConfig {

    @Bean
    public TelegramBot getBot(ApplicationConfig config) {
        if (config.telegramToken() == null) {
            log.error("Empty Telegram-Token!");
        }
        return new TelegramBot(config.telegramToken());
    }
}
