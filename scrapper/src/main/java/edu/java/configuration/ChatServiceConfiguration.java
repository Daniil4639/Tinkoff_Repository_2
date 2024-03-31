package edu.java.configuration;

import edu.java.db_services.ChatService;
import edu.java.domain.jdbc.JdbcChatRepository;
import edu.java.domain.jooq.JooqChatRepository;
import edu.java.domain.jpa.JpaChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatServiceConfiguration {

    @Autowired
    private JdbcChatRepository jdbcChatRepository;
    @Autowired
    private JooqChatRepository jooqChatRepository;
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Bean
    public ChatService getChatService(ApplicationConfig config) {
        switch (config.databaseAccessType()) {
            case JDBC -> {
                return new ChatService(jdbcChatRepository);
            }
            case JOOQ -> {
                return new ChatService(jooqChatRepository);
            }
            case JPA -> {
                return new ChatService(jpaChatRepository);
            }
            default -> {
                return null;
            }
        }
    }
}
