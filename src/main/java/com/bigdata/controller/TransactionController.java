package com.bigdata.controller;


import com.bigdata.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TransactionController {

    @Autowired
    TransactionService service;
}
