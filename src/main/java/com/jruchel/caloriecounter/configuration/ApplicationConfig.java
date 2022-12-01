package com.jruchel.caloriecounter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
public class ApplicationConfig {

    @Bean
    public TaskScheduler taskScheduler(@Value("${THREAD_COUNT}") int threadCount) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(threadCount);
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduler");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
