package com.bigdata.batch;

import com.bigdata.services.ImportServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class
    );

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ImportServiceProxy importServiceProxy;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        logger.info("Job started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job is completed");

            String res = importServiceProxy.generateReports();
            logger.info("report service: ", res);
        }
    }
}
