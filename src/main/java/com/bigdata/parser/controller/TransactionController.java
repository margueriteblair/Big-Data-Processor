package com.bigdata.parser.controller;


import com.bigdata.parser.model.Transaction;
import com.bigdata.parser.service.TransactionService;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("api/")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("test/csv")
    public void postCsvFile(HttpServletRequest request) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        reader.readLine();
        String row;
        StatelessSession session = service.getSession();
        var transaction = session.beginTransaction();
        while((row = reader.readLine()) != null) {
            session.insert(new Transaction(row));
        }

        transaction.commit();
        session.close();
    }

    @GetMapping("/test")
    public String getTest() {
        return "test called works";
    }
}
