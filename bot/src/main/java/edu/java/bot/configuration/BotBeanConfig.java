package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BotBeanConfig {

    public BotBeanConfig() {}

    @Bean
    @Primary
    public ApplicationConfig getConfig() {
        return new ApplicationConfig();
    }

    @Bean
    public TelegramBot getBot(ApplicationConfig config) {
        return new TelegramBot(config.getTelegramToken());
    }
}
