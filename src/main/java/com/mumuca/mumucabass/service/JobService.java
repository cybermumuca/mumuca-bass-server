package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.model.Job;
import com.mumuca.mumucabass.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public String createJob(String trackId) {
        Job job = jobRepository.save(new Job(trackId));
        return job.getId();
    }

    public Job findJobById(String jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }
}
