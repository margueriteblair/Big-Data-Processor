package com.bigdata.services;

import org.springframework.web.bind.annotation.GetMapping;

public interface ImportServiceProxy {

    @GetMapping("api/report/fraud")
    public String generateReports();
}
