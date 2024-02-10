package edu.java.bot.runner;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.BotBeanConfig;
import edu.java.bot.controller.UpdateRunnerBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BotBeanConfig.class);
        ApplicationConfig config = context.getBean("getConfig", ApplicationConfig.class);

        UpdateRunnerBot bot = new UpdateRunnerBot(config.getTelegramToken(), context);

        bot.run();
    }
}
