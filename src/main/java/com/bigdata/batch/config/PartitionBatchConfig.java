package com.bigdata.batch.config;

import com.bigdata.batch.batch.DBWriter;
import com.bigdata.batch.batch.Processor;
import com.bigdata.batch.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

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
}
