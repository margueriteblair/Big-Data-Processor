package com.bigdata.batch.config;

import com.bigdata.batch.batch.DBWriter;
import com.bigdata.batch.batch.Processor;
import com.bigdata.batch.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private static final Logger log = LoggerFactory.getLogger(SpringBatchConfig.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private DBWriter writer;

    @Autowired
    private Processor processor;

    @Autowired
    private Environment env;


    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(20);
        executor.setCorePoolSize(20);
        executor.setQueueCapacity(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        executor.afterPropertiesSet();
        return executor;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("ETL-file-load")
                .<Transaction, Transaction>chunk(10000)
                .reader(itemReader())
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public FlatFileItemReader<Transaction> itemReader() {
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<Transaction>();
        flatFileItemReader.setResource(new FileSystemResource(env.getProperty("file.path")));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1); //first line is the header so we can skip it!
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("step", "type", "amount", "nameOrig", "oldBalanceOrg", "newBalanceOrig", "nameDest", "oldBalanceDest", "newBalanceDest", "isFraud", "isFlaggedFraud");

        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);
        fieldSetMapper.setDistanceLimit(0);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
