package com.bigdata.processor;

import com.bigdata.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
        private static final Logger log = LoggerFactory.getLogger(TransactionProcessor.class);

        @Override
        public Transaction process(final Transaction transaction) throws Exception {

            final int step = transaction.getStep();
            final String type = transaction.getType();
            final BigDecimal amount = transaction.getAmount();
            final String nameOrig = transaction.getNameOrig();
            final BigDecimal oldBalanceOrg = transaction.getOldBalanceOrg();
            final BigDecimal newBalanceOrig = transaction.getNewBalanceOrig();
            final String nameDest = transaction.getNameDest();
            final BigDecimal oldBalanceDest = transaction.getOldBalanceDest();
            final BigDecimal newBalanceDest = transaction.getNewBalanceDest();
            final Boolean isFraud = transaction.getFraud();
            final Boolean isFlaggedFraud = transaction.getFlaggedFraud();

            final Transaction transformedTransaction = new Transaction(step, type,
                    amount, nameOrig, oldBalanceOrg, newBalanceOrig, nameDest,
                    oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud);

            log.info("Converting (" + ") into (" + transformedTransaction + ")");

            return transformedTransaction;

        }

}
