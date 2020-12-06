package com.bigdata.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="bigdata-reports-service", fallback= ReportingError_NoReports.class)
public interface ImportServiceProxy {

    @GetMapping("api/report/fraud")
    public String generateReports();
}
