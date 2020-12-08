package com.bigdata.config;


import com.bigdata.batch.DBWriter;
import com.bigdata.model.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

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

}
