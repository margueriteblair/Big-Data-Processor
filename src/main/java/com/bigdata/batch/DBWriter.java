package com.bigdata.batch;

import com.bigdata.model.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter {

    @Autowired
    private TransactionRepository repository;

    @Override
    public void write(List<? extends Transaction> transactions) throws Exception) {
        repository.saveAll(transactions);
    }
}
