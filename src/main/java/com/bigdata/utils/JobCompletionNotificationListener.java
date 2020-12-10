//package com.bigdata.utils;
//
//import com.bigdata.model.Transaction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.BatchStatus;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.listener.JobExecutionListenerSupport;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
//    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class
//    );
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void afterJob(JobExecution jobExecution) {
//        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
//            logger.info("----- JOB FINISHED ----- VERIFYING RESULTS ...\n");
//            List<Transaction> results = jdbcTemplate.query("SELECT * FROM transactions", new RowMapper<Transaction>() {
//                @Override
//                public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
//                    return new Transaction(resultSet.getInt(1), resultSet.getString(2), resultSet.getBigDecimal(3), resultSet.getString(4), resultSet.getBigDecimal(5), resultSet.getBigDecimal(6),
//                            resultSet.getString(7), resultSet.getBigDecimal(8), resultSet.getBigDecimal(9), resultSet.getBoolean(10), resultSet.getBoolean(11));
//                }
//            });
//
//            for (Transaction transaction : results) {
//                logger.info("Discovered <" + transaction + "> in the database.");
//            }
//        }
//    }
//
//
//
//}
