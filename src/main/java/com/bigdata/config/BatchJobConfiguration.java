package com.bigdata.config;


import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchJobConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public JobLauncher jobLauncher() throws Exception {

    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadExecutor = new ThreadPoolTaskExecutor();
        threadExecutor.setCorePoolSize(8);
        threadExecutor.setMaxPoolSize(12);
        threadExecutor.setQueueCapacity(15);

        return threadExecutor;
    }
}
