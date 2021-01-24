package com.bigdata.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Main {


    public static void main(String[] args) {
//        String[] newArgs = new String[] {"inputFlatFile=src/main/resources/data/PS_20174392719_1491204439457_log.csv"};
        SpringApplication.run(com.bigdata.batch.Main.class, args);
    }
}