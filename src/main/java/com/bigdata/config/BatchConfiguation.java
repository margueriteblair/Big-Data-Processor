package com.bigdata.config;

import com.bigdata.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.core.task.TaskExecutor;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class BatchConfiguation {
    private static int CHUNK = 5000;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<Transaction> itemReader, ItemProcessor<Transaction, Transaction> itemProcessor,
                   ItemWriter<Transaction> itemWriter) {
        Step step = stepBuilderFactory.get("MMTransaction-file-load")
                .<Transaction, Transaction>chunk(CHUNK)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();

        return jobBuilderFactory.get("MMTransaction-load")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int poolSize = 20;
        executor.setMaxPoolSize(poolSize);
        executor.setCorePoolSize(poolSize);
        executor.setQueueCapacity(10);
        executor.afterPropertiesSet();
        return executor;
    }

}
