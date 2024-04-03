package edu.java.configuration;

import edu.java.domain.jpa.entities.ChatEntity;
import edu.java.domain.jpa.entities.ConnectionEntity;
import edu.java.domain.jpa.entities.LinkEntity;
import org.hibernate.SessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "spring.jpa.properties")
public class HibernateConfiguration {

    @Bean(name = "entityManagerFactory")
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
            .addAnnotatedClass(ChatEntity.class)
            .addAnnotatedClass(LinkEntity.class)
            .addAnnotatedClass(ConnectionEntity.class)
            .buildSessionFactory();
    }
}
