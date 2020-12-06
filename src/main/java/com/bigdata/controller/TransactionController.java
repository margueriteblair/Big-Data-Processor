package com.bigdata.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.batch.operations.JobExecutionIsRunningException;
import javax.batch.operations.JobRestartException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dataproc")
public class TransactionController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping("/load")
    public ResponseEntity<?> load() {
        try {
            ChunkFile.startChunking();
            //SplitBigFile.startSplit();

            Map<String, JobParameter> map = new HashMap<>();
            map.put("current runtime", new JobParameter(System.currentTimeMillis()));
            jobLauncher.run(job, new JobParameters(map));

            return ResponseEntity.status(HttpStatus.OK).body("Successfully chunking data");

        } catch (JobRestartException | JobExecutionIsRunningException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing data.");
        }
    }
}
