package edu.java.configuration.monitor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorAppConfiguration {

    @Bean
    public MeterRegistry getMeterRegistry() {
        return new CompositeMeterRegistry();
    }
}
