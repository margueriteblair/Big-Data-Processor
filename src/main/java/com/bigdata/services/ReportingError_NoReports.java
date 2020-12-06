package com.bigdata.services;


import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
public class ReportingError_NoReports implements ImportServiceProxy {


    @Override
    public String generateReports() {
        return "No reports available.";
    }
}
