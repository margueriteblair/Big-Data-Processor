package com.bigdata.batch.batch;

import com.bigdata.batch.model.Transaction;
import com.bigdata.batch.repository.TransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Transaction> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void write(List<? extends Transaction> listOfTxs) throws Exception {
        System.out.println("Data being saved for transactions: " + listOfTxs);
        transactionRepository.saveAll(listOfTxs);
    }
}

