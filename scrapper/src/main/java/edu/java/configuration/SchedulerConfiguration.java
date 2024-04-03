package edu.java.configuration;

import edu.java.scheduler.SchedulerService;
import edu.java.scheduler.daos.JdbcSchedulerDao;
import edu.java.scheduler.daos.JooqSchedulerDao;
import edu.java.scheduler.daos.JpaSchedulerDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfiguration {

    @Bean
    public long schedulerInterval(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
    public SchedulerService getJdbcService(JdbcSchedulerDao jdbcSchedulerDao,
        GitHubService gitHubService,
        StackOverFlowService stackOverFlowService) {

        return new SchedulerService(gitHubService, stackOverFlowService, jdbcSchedulerDao);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
    public SchedulerService getJooqService(JooqSchedulerDao jooqSchedulerDao,
        GitHubService gitHubService,
        StackOverFlowService stackOverFlowService) {

        return new SchedulerService(gitHubService, stackOverFlowService, jooqSchedulerDao);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
    public SchedulerService getJpaService(JpaSchedulerDao jpaSchedulerDao,
        GitHubService gitHubService,
        StackOverFlowService stackOverFlowService) {

        return new SchedulerService(gitHubService, stackOverFlowService, jpaSchedulerDao);
    }
}
