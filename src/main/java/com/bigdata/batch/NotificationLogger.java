package com.bigdata.batch;

import com.bigdata.services.ImportServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class NotificationLogger extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(NotificationLogger.class
    );

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ImportServiceProxy importServiceProxy;

    @Override
    public void afterRun(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job is completed. Time to call Reports Service.");

            String res = importServiceProxy.generateReports();
            logger.info("report service: ", res);
        }
    }
}
