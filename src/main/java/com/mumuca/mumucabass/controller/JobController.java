package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.dto.request.JobStatusRequest;
import com.mumuca.mumucabass.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/status")
    public ResponseEntity<?> receiveJobStatus(@Valid @RequestBody JobStatusRequest request) {
        jobService.updateJobStatus(request.jobId(), request.status());

        return ResponseEntity.ok().build();
    }
}
