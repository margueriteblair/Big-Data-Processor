package com.bigdata.batch;

import com.bigdata.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

@Component
public class Processor implements ItemProcessor<Transaction, Transaction>{

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        return transaction;
    }
}
