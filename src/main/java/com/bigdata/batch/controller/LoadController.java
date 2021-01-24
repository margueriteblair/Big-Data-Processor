package com.bigdata.batch.controller;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, IOException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("inputFlatFile", new JobParameter("src/main/resources/data/PS_20174392719_1491204439457_log.csv"));
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        jobLauncher.run(job, parameters);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        System.out.println("Job Execution: " + jobExecution.getStatus() + jobExecution.getAllFailureExceptions() + "\n"
                + jobExecution.getFailureExceptions() + jobExecution.getExitStatus()
        );

        System.out.println("We're done! Please check results");

        return jobExecution.getStatus();
    }

    @GetMapping("/test")
    public String testString() {
        return "test worked!";
    }
}
