package com.bigdata.config;


import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class PartitioningBatchConfiguration {
    private static Logger logger = (Logger) LoggerFactory.getLogger(PartitioningBatchConfiguration.class);

}
