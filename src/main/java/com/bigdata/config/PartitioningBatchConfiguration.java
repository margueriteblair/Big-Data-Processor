package com.bigdata.config;


import com.bigdata.batch.DBWriter;
import com.bigdata.batch.JobCompletionNotificationListener;
import com.bigdata.batch.Processor;
import com.bigdata.model.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class PartitioningBatchConfiguration {
    private static Logger logger = (Logger) LoggerFactory.getLogger(PartitioningBatchConfiguration.class);

    private static final int CHUNK = 10000;
    private static final int POOL_SIZE = 10;
    private static final int GRID_SIZE = 4;
    private static final int MAX_POOL = 16;
    private static final int QUEUE = 10;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DBWriter dbWriter;

    @Autowired
    private Processor itemProcessor;

    @Autowired
    private FlatFileItemReader<Transaction> itemReader;

    @Bean("partitioner")
    @StepScope
    public Partitioner partitioner() {
        logger.info("In partitioner");

        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = null;
        try {
            resources = resolver.getResources("file:" + "*.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        partitioner.setResources(resources);
        partitioner.partition(GRID_SIZE);

        return partitioner;
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
    @Bean
    @StepScope
    @Qualifier("itemReader")
    @DependsOn("partitioner")
    public FlatFileItemReader<Transaction> itemReader(@Value("#{stepExecutionContext['fileName']}") String filename) throws MalformedURLException {

        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new UrlResource(filename));

        flatFileItemReader.setName("MMCSV-Reader");
//		flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL);
        taskExecutor.setCorePoolSize(POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE);
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
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Transaction, Transaction>chunk(CHUNK)
                .processor(itemProcessor)
                .writer(dbWriter)
                .reader(itemReader)
                .build();
    }
    @Bean
    public Job job(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("MMTransaction-load")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(masterStep())
                .end()
                .build();
    }


}
