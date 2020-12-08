package com.bigdata.utils;

import com.bigdata.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class
    );

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("----- JOB FINISHED ----- VERIFYING RESULTS ...\n");
            List<Transaction> results = jdbcTemplate.query("SELECT * FROM transactions", new RowMapper<Transaction>() {
                @Override
                public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new Transaction(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6))
                }
            });

            for (Transaction transaction : results) {
                logger.info("Discovered <" + transaction + "> in the database.");
            }
        }
    }



}
