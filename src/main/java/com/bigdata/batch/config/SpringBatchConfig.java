package com.bigdata.batch.config;

import com.bigdata.batch.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {


    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Transaction> itemReader,
                   ItemProcessor<Transaction, Transaction> itemProcessor,
                   ItemWriter<Transaction> itemWriter) {

//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setMaxPoolSize(16);
//        taskExecutor.afterPropertiesSet();

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Transaction, Transaction>chunk(5000)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
//                .taskExecutor(taskExecutor)
                .build();

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

    }

    @Bean
    public FlatFileItemReader<Transaction> itemReader() {
        //@Value("${file.path}") Resource resource
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<Transaction>();
        flatFileItemReader.setResource(new FileSystemResource("/Users/margueriteblair/Desktop/PS_20174392719_1491204439457_log.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1); //first line is the header so we can skip it!
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("step", "type", "amount", "nameorig", "oldbalanceorg", "newbalanceorig", "namedest", "oldbalancedest", "newbalancedest", "isfraud", "isflaggedfraud");

        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public JdbcBatchItemWriter<Transaction> batchWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transaction>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO transactions (step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud) VALUES (:step, :type, :amount, :nameorig, :oldbalanceorg, :newbalanceorig, :namedest, :oldbalancedest, :newbalancedest, :isfraud, :isflaggedfraud)")
                .dataSource(dataSource)
                .build();
    }

//    @Bean
//    public Step step1() {
//
//        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
//        ItemProcessor<Transaction, Transaction> itemProcessor = new ItemProcessor<>() {
//            @Override
//            public Transaction process(Transaction transaction) throws Exception {
//                return null;
//            }
//        };
//        ItemReader<Transaction> reader = new ItemReader<Transaction>() {
//            @Override
//            public Transaction read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                return null;
//            }
//        };
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setMaxPoolSize(10);
//        taskExecutor.afterPropertiesSet();//all for multithreading
//
//        return stepBuilderFactory.get("step1")
//                .<Transaction, Transaction> chunk(5000)
//                .reader(reader)
//                .processor(itemProcessor)
//                .writer(writer)
//                .taskExecutor(taskExecutor) //use for multithreading
//                .build();
//    }
}
