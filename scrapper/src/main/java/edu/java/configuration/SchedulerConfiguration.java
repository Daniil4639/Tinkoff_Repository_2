package edu.java.configuration;

import edu.java.scheduler.SchedulerService;
import edu.java.scheduler.daos.JdbcSchedulerDao;
import edu.java.scheduler.daos.JooqSchedulerDao;
import edu.java.scheduler.daos.JpaSchedulerDao;
import edu.java.service.GitHubService;
import edu.java.service.StackOverFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfiguration {

    @Autowired
    private JdbcSchedulerDao jdbcSchedulerDao;
    @Autowired
    private JooqSchedulerDao jooqSchedulerDao;
    @Autowired
    private JpaSchedulerDao jpaSchedulerDao;

    @Bean
    public long schedulerInterval(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    public SchedulerService getSchedulerDao(ApplicationConfig config, GitHubService gitHubService,
        StackOverFlowService stackOverFlowService) {

        switch (config.databaseAccessType()) {
            case AccessType.JDBC -> {
                return new SchedulerService(gitHubService, stackOverFlowService, jdbcSchedulerDao);
            }
            case AccessType.JOOQ -> {
                return new SchedulerService(gitHubService, stackOverFlowService, jooqSchedulerDao);
            }
            case AccessType.JPA -> {
                return new SchedulerService(gitHubService, stackOverFlowService, jpaSchedulerDao);
            }
            default -> {
                return null;
            }
        }
    }
}
