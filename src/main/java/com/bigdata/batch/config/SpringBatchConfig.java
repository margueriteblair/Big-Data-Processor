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
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope //A spring batch StepScope object is one which is unique to a specific step
    //as opposed to a singleton. The default bean scope in Spring is a singleton, but by specifying
    //StepScope, spring batch will use the spring container to instantiate a new instance of this component for each step
    //of execution.
    public FlatFileItemReader<Transaction> fileTransactionReader() {
        return new FlatFileItemReaderBuilder<Transaction>()
                .linesToSkip(1)
                .name("transactionItemReader")
                .resource(new ClassPathResource("/data/PS_20174392719_1491204439457_log.csv"))
                .delimited()
                .names(new String[] {"step", "type", "amount", "nameOrig", "oldBalanceOrg", "newBalanceOrig", "nameDest", "oldBalanceDest", "newBalanceDest", "isFraud", "isFlaggedFraud"})
                .fieldSetMapper(fieldSet -> {
                    Transaction transaction = new Transaction();
                    transaction.setStep(fieldSet.readInt("step"));
                    transaction.setType(fieldSet.readString("type"));
                    transaction.setAmount(fieldSet.readBigDecimal("amount"));
                    transaction.setNameOrig(fieldSet.readString("nameOrig"));
                    transaction.setOldBalanceOrg(fieldSet.readBigDecimal("oldBalanceOrg"));
                    transaction.setNewBalanceOrig(fieldSet.readBigDecimal("newBalanceOrig"));
                    transaction.setNameDest(fieldSet.readString("nameDest"));
                    transaction.setOldBalanceDest(fieldSet.readBigDecimal("oldBalanceDest"));
                    transaction.setNewBalanceDest(fieldSet.readBigDecimal("newBalanceDest"));
                    transaction.setIsFraud(fieldSet.readInt("isFraud"));
                    transaction.setIsFlaggedFraud(fieldSet.readInt("isFlaggedFraud"));

                    return transaction;
                })
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO TRANSACTION (step, type, amount, nameOrig, oldBalanceOrg, newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud) VALUES (:step, :type, :amount, :nameOrig, :oldBalanceOrg, :newBalanceOrig, :nameDest, :oldBalanceDest, :newBalanceDest, :isFraud, :isFlaggedFraud)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job multithreadedJob() {
        return this.jobBuilderFactory.get("multithreadedJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setCorePoolSize(4);
        taskExecutor.afterPropertiesSet();

        return this.stepBuilderFactory.get("step1")
                .<Transaction, Transaction>chunk(10000)
                .reader(fileTransactionReader())
                .writer(writer(null))
                .taskExecutor(taskExecutor)
                .build();
    }
}
