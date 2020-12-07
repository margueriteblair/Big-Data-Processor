package com.bigdata.config;

import com.bigdata.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
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

    @Bean
    public FlatFileItemReader<Transaction> itemReader() {
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("data/PS_Sample_log.csv"));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.setName("MMCSV-Reader");

        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
//TODO: See if this needs to be in the form of an object
        lineTokenizer.setNames("step", "type", "amount", "nameOrig", "oldBalanceOrig", "newBalanceOrig", "nameDestination", "oldBalanceDestination", "newBlanaceDestination", "isFraud", "isFlaggedFraud");

        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);
        fieldSetMapper.setDistanceLimit(0);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;

    }

}
