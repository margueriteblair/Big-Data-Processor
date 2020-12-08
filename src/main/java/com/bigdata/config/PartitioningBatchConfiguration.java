package com.bigdata.config;


import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

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

}
