package com.bigdata.parser.service;


import com.bigdata.parser.model.Transaction;
import com.bigdata.parser.repository.TransactionRepository;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository database;

    @Autowired
    SessionFactory sessionFactory;

    public StatelessSession getSession() {
        return sessionFactory.openStatelessSession();
    }

    public List<Transaction> findAllTxs() {
        return database.findAll();
    }
}
