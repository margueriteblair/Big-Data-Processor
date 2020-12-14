//package com.bigdata.config;
//
//import com.bigdata.model.Transaction;
//import com.bigdata.processor.TransactionProcessor;
//import com.bigdata.utils.JobCompletionNotificationListener;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//
//import javax.sql.DataSource;
//
//public class CsvFileToDBConfig {
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    public DataSource dataSource;
//
//    //below here we start the reader writer and processor
//
//    @Bean
//    public ItemProcessor<Transaction, Transaction> csvTransactionProcessor() {
//        return new TransactionProcessor();
//    }
//
//    @Bean
//    public FlatFileItemReader<Transaction> csvTransactionReader(){
//        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
//        reader.setResource(new ClassPathResource("transactioncsv.csv"));
//        reader.setLineMapper(new DefaultLineMapper<Transaction>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames(new String[] { "step", "type", "amount", "nameOrig", "oldBalanceOrg", "newBalanceOrg", "nameDest", "oldBalanceDest", "isFraud", "isFlaggedFraud"});
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {{
//                setTargetType(Transaction.class);
//            }});
//        }});
//        return reader;
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Transaction> csvTransactionWriter() {
//        JdbcBatchItemWriter<Transaction> csvTransactionWriter = new JdbcBatchItemWriter<>();
//        csvTransactionWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        csvTransactionWriter.setSql("INSERT INTO transactions (step, type, amount, nameOrig, oldBalanceOrg, newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud) VALUES (:step, :type, :amount, :nameOrig, :oldBalanceOrg, :newBalanceOrig, :nameDest, :oldBalanceDest, :newBalanceDest, :isFraud, :isFlaggedFraud)");
//        csvTransactionWriter.setDataSource(dataSource);
//        return csvTransactionWriter;
//    }
//
//    @Bean
//    public Step csvFileToDBStep() {
//        return stepBuilderFactory.get("csvFileToDBStep")
//                .<Transaction, Transaction>chunk(1)
//                .reader(csvTransactionReader())
//                .writer(csvTransactionWriter())
//                .build();
//    }
//
//    @Bean
//    Job csvFileToDBJob(JobCompletionNotificationListener listener) {
//        return jobBuilderFactory.get("csvFileToDBJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(csvFileToDBStep())
//                .end()
//                .build();
//    }
//}
