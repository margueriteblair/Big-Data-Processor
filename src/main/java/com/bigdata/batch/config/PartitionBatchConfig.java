package com.bigdata.batch.config;

import com.bigdata.batch.batch.DBWriter;
import com.bigdata.batch.batch.Processor;
import com.bigdata.batch.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.net.MalformedURLException;

public class PartitionBatchConfig {
    private static final Logger log = LoggerFactory.getLogger(PartitionBatchConfig.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DBWriter itemWriter;

    @Autowired
    private Processor itemProcessor;

    @Autowired
    private FlatFileItemReader<Transaction> itemReader;

    @Bean("partitioner")
    @StepScope
    public Partitioner partitioner() {
        log.info("In Partitioner");

        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = null;

        try {
            resources = resolver.getResources("file:" + "*.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        partitioner.setResources(resources);
        partitioner.partition(4);
        return partitioner;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("ETL-file-load")
                .incrementer(new RunIdIncrementer())
                .flow(masterStep())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Transaction, Transaction>chunk(10000)
                .processor(itemProcessor)
                .writer(itemWriter)
                .reader(itemReader)
                .build();
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    @Qualifier("masterStep")
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep")
                .partitioner("step1", partitioner())
                .step(step1())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    @Qualifier("itemReader")
    @DependsOn("partitioner")
    public FlatFileItemReader<Transaction> itemReader(@Value("#{stepExecutionContext['fileName']}")String filename) throws MalformedURLException {
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new UrlResource(filename));
        flatFileItemReader.setName("MMCSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);

        lineTokenizer.setNames(new String[] { "step", "type", "amount", "nameOrig",
                "oldBalanceOrg", "newBalanceOrig", "nameDest", "oldBalanceDest",
                "newBalanceDest", "isFraud", "isFlaggedFraud" });

        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);
        fieldSetMapper.setDistanceLimit(0);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
